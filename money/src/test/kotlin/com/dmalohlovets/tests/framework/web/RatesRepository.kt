package com.dmalohlovets.tests.framework.web

import com.dmalohlovets.tests.framework.web.pojo.Rates
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional

@Repository
class RatesRepository(
    enhancedClient: DynamoDbEnhancedClient,
    @Value("\${app.aws.db}") tableName: String,
) {
    val table =
        enhancedClient.table(
            tableName,
            TableSchema.fromBean(Rates::class.java),
        )

    fun save(rate: Rates) = table.putItem(rate)

    fun findById(id: String): Rates? = table.getItem(Key.builder().partitionValue(id).build())

    fun findAll(): List<Rates> = table.scan().items().toList()

    fun deleteById(id: String) = table.deleteItem(Key.builder().partitionValue(id).build())

    fun findBySource(source: String): List<Rates> {
        val index = table.index("source-index")

        return index.query(
            QueryConditional.keyEqualTo(
                Key.builder().partitionValue(source).build(),
            ),
        ).flatMap { it.items() }.toList()
    }

    fun saveAll(items: List<Rates>) = items.forEach { table.putItem(it) }
}
