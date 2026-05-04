package com.dmalohlovets.tests.framework.web.pojo

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey

@DynamoDbBean
data class Rates(
    @get:DynamoDbAttribute("max")
    var max: String = "",
    @get:DynamoDbAttribute("min")
    var min: String = "",
    @get:DynamoDbAttribute("source")
    var source: String = "",
    @get:DynamoDbAttribute("date!")
    @get:DynamoDbSecondaryPartitionKey(indexNames = ["circle-date-index"])
    var date: String = "",
    @get:DynamoDbPartitionKey
    @get:DynamoDbAttribute("circle")
    var circle: String = "",
)
