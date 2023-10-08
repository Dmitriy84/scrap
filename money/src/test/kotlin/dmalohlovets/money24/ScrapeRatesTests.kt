package dmalohlovets.money24

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.PutItemRequest
import dmalohlovets.framework.web.WebBaseTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.AfterAll
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
    fun scrap() = runTest(timeout = 13.hours) {
        if (!Files.exists(Path.of(OUTPUT_FILE)))
            FileOutputStream(OUTPUT_FILE, true).writeCsv("min", "max", "date")

        repeat(24) {
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
                    "date" to date,
                    "min" to min,
                    "max" to max,
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

    @Autowired
    private lateinit var mainPage: MainPage

    private fun OutputStream.writeCsv(data: List<Array<String>>) = bufferedWriter().use {
        data.map { arr -> arr.joinToString(",", postfix = "\n") }
            .forEach(it::write)
        it.flush()
    }

    private fun OutputStream.writeCsv(vararg data: String) = bufferedWriter().use {
        it.write(data.joinToString(",", postfix = "\n"))
        it.flush()
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