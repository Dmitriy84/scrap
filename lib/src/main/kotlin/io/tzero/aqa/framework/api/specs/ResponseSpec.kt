package io.tzero.aqa.framework.api.specs

import io.restassured.builder.ResponseSpecBuilder

object ResponseSpec {
    val `200`
        get() = 200.toResponseSpec()
    val `201`
        get() = 201.toResponseSpec()
    val `400`
        get() = 400.toResponseSpec()
    val `404`
        get() = 404.toResponseSpec()

    private fun Int.toResponseSpec() = ResponseSpecBuilder()
        .expectStatusCode(this)
        .build()!!
}
