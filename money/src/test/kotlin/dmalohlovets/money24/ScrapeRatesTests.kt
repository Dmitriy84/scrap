package dmalohlovets.money24

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.PutItemRequest
import aws.sdk.kotlin.services.dynamodb.model.ScanRequest
import aws.sdk.kotlin.services.sns.SnsClient
import aws.sdk.kotlin.services.sns.model.PublishRequest
import aws.smithy.kotlin.runtime.InternalApi
import aws.smithy.kotlin.runtime.util.toNumber
import dmalohlovets.framework.web.WebBaseTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

private const val OUTPUT_FILE = "rates.csv"

@EnableConfigurationProperties(ProjectConfig::class)
class ScrapeRatesTests : WebBaseTest() {
    @Test
    @Tag("scrap")
    fun scrap() = runTest(timeout = 13.hours) {
//        pubTextSMS("AWS Rocks !!!", "+380634596992")

        if (!Files.exists(Path.of(OUTPUT_FILE)))
            FileOutputStream(OUTPUT_FILE, true).writeCsv("min", "max", "date")

        repeat(1) {
            if (!isCI)
                driver.manage().window().minimize()
            driver[url]

            val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            val min = mainPage.min[0].text.split("\n")[1]
            val max = mainPage.max[0].text.split("\n")[1]

            async(Dispatchers.IO) {
                FileOutputStream(OUTPUT_FILE, true).writeCsv(min, max, date)
            }

            async {
                val itemValues = mapOf(
                    "date!" to date,
                    "min" to min,
                    "max" to max,
                    "circle" to System.getenv("CIRCLE_WORKFLOW_ID"),
                ).mapValues { AttributeValue.S(it.value) }

                val request = PutItemRequest {
                    tableName = aws_db
                    item = itemValues
                }

                DynamoDbClient { region = aws_region }.use { ddb ->
                    ddb.putItem(request)
                }
            }

            if (!isCI)
                async {
                    withContext(Dispatchers.Default) {
                        delay(30.minutes)
                        driver.navigate().refresh()
                    }
                }.await()
        }
    }

    @OptIn(InternalApi::class)
    @Tag("analysis")
    @Test
    fun `analyze previous and current rates`() = runTest {
        //TODO replace by Query with sort and limit
        val request = ScanRequest {
            tableName = aws_db
            select
        }

        val (first, second) = DynamoDbClient {
            region = aws_region
        }.use { client -> client.scan(request).items?.sortedByDescending { it["date!"].toString() }?.subList(0, 2)!! }

        if (first["max"]?.asS()?.toNumber() != second["max"]?.asS()?.toNumber()
            || first["min"]?.asS()?.toNumber() != second["min"]?.asS()?.toNumber()
        )
            pubTextSMS(
                "Was max: ${second["max"]}, min: ${first["min"]}; Now max: ${first["max"]}, min: ${first["min"]}",
                app_mobile
            )
    }

    @Autowired
    private lateinit var mainPage: MainPage

    private fun OutputStream.writeCsv(vararg data: String) = bufferedWriter().use {
        it.write(data.joinToString(",", postfix = "\n"))
        it.flush()
    }

    private suspend fun pubTextSMS(messageVal: String?, phoneNumberVal: String?) {
        val request = PublishRequest {
            message = messageVal
            phoneNumber = phoneNumberVal
        }

        SnsClient { region = aws_region }.use {
            println("${it.publish(request).messageId} message sent.")
        }
    }

    companion object {
        @JvmStatic
        @AfterAll
        internal fun finish() {
            if (!isCI)
                Runtime.getRuntime().exec(arrayOf("open", OUTPUT_FILE))
        }
    }
}
