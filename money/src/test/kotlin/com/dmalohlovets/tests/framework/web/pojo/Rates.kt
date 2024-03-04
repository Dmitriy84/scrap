package com.dmalohlovets.tests.framework.web.pojo

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable


@DynamoDBTable(tableName = "money24_rates")
open class Rates(
    @field:DynamoDBAttribute
    var max: String = "",

    @field:DynamoDBAttribute
    var min: String = "",

    @field:DynamoDBAttribute
    var source: String = "",

    @field:DynamoDBAttribute(attributeName = "date!")
    @field:DynamoDBIndexHashKey(globalSecondaryIndexName = "circle-date-index")
    var date: String = "",

    @field:DynamoDBHashKey
    @field:DynamoDBAttribute
    var circle: String = "",
)
