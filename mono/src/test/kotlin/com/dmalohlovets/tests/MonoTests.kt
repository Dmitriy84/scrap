package com.dmalohlovets.tests

import aqa.framework.utils.SpecUtils.extractJsonValues
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.PutItemRequest
import com.dmalohlovets.tests.api.base.BaseApiTest
import io.restassured.RestAssured.get
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class MonoTests : BaseApiTest() {
    @Test
    @Tag("scrap")
    @Tag("mono")
    fun getRates() = runTest {
        val (date, min, max) = get("/bank/currency")
            .then()
            .extractJsonValues("[0].date", "[0].rateBuy", "[0].rateSell")

        val itemValues = mapOf(
            "date!" to date,
            "min" to min,
            "max" to max,
            "source" to "mono",
            "circle" to System.getenv("CIRCLE_WORKFLOW_ID").orEmpty(),
        ).mapValues { AttributeValue.S(it.value) }

        DynamoDbClient { region = aws_region }.use { ddb ->
            PutItemRequest {
                tableName = aws_db
                item = itemValues
            }.run {
                ddb.putItem(this)
            }
        }
    }
}
