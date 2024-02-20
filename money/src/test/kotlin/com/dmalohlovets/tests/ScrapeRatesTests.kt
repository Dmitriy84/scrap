package com.dmalohlovets.tests

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.ScanRequest
import aws.sdk.kotlin.services.sns.SnsClient
import aws.sdk.kotlin.services.sns.model.PublishRequest
import aws.smithy.kotlin.runtime.InternalApi
import aws.smithy.kotlin.runtime.util.toNumber
import com.dmalohlovets.tests.framework.web.base.WebBaseTest
import com.dmalohlovets.tests.framework.web.config.ProjectConfig
import com.dmalohlovets.tests.money24.pages.Money24MainPage
import com.dmalohlovets.tests.sense.pages.SenseMainPage
import com.dmalohlovets.tests.tests.config.components.RatesDynamoDbInserter
import com.dmalohlovets.tests.tests.config.components.RatesFileInserter
import com.dmalohlovets.tests.tests.config.interfaces.DataInserter
import com.dmalohlovets.tests.tests.config.interfaces.DataInserter.Companion.dateOf
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
import java.nio.file.Files
import java.nio.file.Path
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes


private const val OUTPUT_FILE = "rates.csv"

@Tag("scrap")
@EnableConfigurationProperties(ProjectConfig::class)
class ScrapeRatesTests : WebBaseTest() {
    @Test
    @Tag("sense")
    fun scrapSense() = runTest {
        driver[banks["sense"]]

        with(senseMainPage) {
            onlineRatesBtn.click()
            usdField.text.split("\n").run {
                ratesDynamoDbInserter.putItem(dateOf(), this[2], this[0], "sense")
            }
        }
    }

    @Test
    @Tag("money24")
    fun scrapMoney24() = runTest(timeout = 13.hours) {
//        pubTextSMS("AWS Rocks !!!", "+380634596992")

        if (!Files.exists(Path.of(OUTPUT_FILE)))
            ratesFileInserter.putItem(source = "source")

        repeat(1) {
            if (!isCI)
                driver.manage().window().minimize()
            driver[banks["money24"]]

            DataInserter.batchProcessor(
                dateOf(),
                money24MainPage.max[0].text.split("\n")[1],
                money24MainPage.min[0].text.split("\n")[1],
                "money24",
                consumers = arrayOf(
                    ratesFileInserter,
                    ratesDynamoDbInserter
                )
            )

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
//        val request2 = QueryRequest {
//            tableName = aws_db
//            scanIndexForward = true
//            limit = 10
//            keyConditionExpression = "#key > :dt"
//            expressionAttributeNames = mapOf("#key" to "date!")
//            expressionAttributeValues =
//                mapOf(
//                    ":dt" to AttributeValue.S(
//                        SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date().time - 3L * 24L * 60L * 60L * 1000L)
//                    )
//                )
////            expressionAttributeValues = mapOf(":dt" to AttributeValue.S("2023-12-21 07:33"))
//        }
//        DynamoDbClient { region = "eu-north-1" }.use { ddb ->
//            val response = ddb.query(request2)
//            println(response.items)
//
//        }


        //TODO replace by Query with sort and limit
        val request = ScanRequest {
            tableName = aws_db
            limit = 2
            indexName = "circle-date-index"
        }

        val (first, second) = DynamoDbClient {
            region = aws_region
        }.use { client ->
            client.scan(request).items?.sortedByDescending { it["date!"].toString() }
                ?.subList(0, 2)!!
        }

        if (first["max"]?.asS()?.toNumber() != second["max"]?.asS()?.toNumber()
            || first["min"]?.asS()?.toNumber() != second["min"]?.asS()?.toNumber()
        )
            pubTextSMS(
                "Was max: ${second["max"]}, min: ${first["min"]}; Now max: ${first["max"]}, min: ${first["min"]}",
                app_mobile
            )
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

    @Autowired
    private lateinit var money24MainPage: Money24MainPage

    @Autowired
    private lateinit var senseMainPage: SenseMainPage

    @Autowired
    private lateinit var ratesDynamoDbInserter: RatesDynamoDbInserter

    @Autowired
    private lateinit var ratesFileInserter: RatesFileInserter

    companion object {
        @JvmStatic
        @AfterAll
        internal fun finish() {
            if (!isCI)
                Runtime.getRuntime().exec(arrayOf("open", OUTPUT_FILE))
        }
    }
}