package io.tzero.aqa.framework.api.specs

import io.restassured.builder.ResponseSpecBuilder

object ResponseSpec {
    val `200` = 200.toResponseSpec()
    val `201` = 201.toResponseSpec()
    val `400` = 400.toResponseSpec()

    private fun Int.toResponseSpec() = ResponseSpecBuilder()
        .expectStatusCode(this)
        .build()!!
}
