package com.dmalohlovets.tests.tests.config.components

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.PutItemRequest
import com.dmalohlovets.tests.tests.config.interfaces.DataInserter
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class RatesDynamoDbInserter(
    @Value("\${app.aws.region}") private val awsRegion: String,
    @Value("\${app.aws.db}") private val awsDb: String
) : DataInserter {
    override fun putItem(date: String, max: String, min: String, source: String) = runTest {
        async {
            val itemValues = mapOf(
                "date!" to date,
                "min" to min,
                "max" to max,
                "source" to source,
                "circle" to System.getenv("CIRCLE_WORKFLOW_ID").orEmpty(),
            )

            PutItemRequest {
                tableName = awsDb
                item = itemValues.mapValues { AttributeValue.S(it.value) }
            }.run {
                DynamoDbClient { region = awsRegion }.use { it.putItem(this) }
            }
        }
    }
}
