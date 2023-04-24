package io.tzero.aqa.framework.api.specs

import io.restassured.builder.ResponseSpecBuilder
import io.tzero.aqa.framework.api.config.ProjectConfig
import org.hamcrest.Matchers

object ResponseSpec {
    val `200` = 200.toResponseSpec()

    val `201` = 201.toResponseSpec()

    val `400` = 400.toResponseSpec()

    private fun Int.toResponseSpec() = ResponseSpecBuilder()
        .expectResponseTime(Matchers.lessThan(ProjectConfig.timeoutStatic))
        .expectStatusCode(this)
        .build()!!
}
