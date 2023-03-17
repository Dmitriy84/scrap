package com.tzero.aqa.framework.api.specs.request

import io.restassured.builder.ResponseSpecBuilder

object ResponseSpec {
    val `200` = ResponseSpecBuilder()
        .expectStatusCode(200)
        .build()!!

    val `201` = ResponseSpecBuilder()
        .expectStatusCode(201)
        .build()!!
}
