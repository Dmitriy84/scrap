package io.tzero.aqa.framework.api.specs

import io.restassured.builder.ResponseSpecBuilder
import io.tzero.aqa.framework.api.config.ProjectConfig
import org.hamcrest.Matchers

object ResponseSpec {
    val `200` = ResponseSpecBuilder()
        .expectResponseTime(Matchers.lessThan(ProjectConfig.timeoutStatic))
        .expectStatusCode(200)
        .build()!!

    val `201` = ResponseSpecBuilder()
        .expectResponseTime(Matchers.lessThan(ProjectConfig.timeoutStatic))
        .expectStatusCode(201)
        .build()!!

    val `400` = ResponseSpecBuilder()
        .expectResponseTime(Matchers.lessThan(ProjectConfig.timeoutStatic))
        .expectStatusCode(400)
        .build()!!
}
