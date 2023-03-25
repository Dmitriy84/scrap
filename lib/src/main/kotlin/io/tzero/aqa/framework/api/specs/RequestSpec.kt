package io.tzero.aqa.framework.api.specs

import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType

object RequestSpec {
    val json = RequestSpecBuilder()
        .setContentType(ContentType.JSON)
        .build()!!

    fun base(xrftoken: String, tzmCookie: String) = RequestSpecBuilder()
        .setContentType(ContentType.JSON)
        .addHeaders(
            mapOf(
                "X-TZM-XSRF-TOKEN" to xrftoken,
                "Cookie" to "tzm-access=$tzmCookie",
            )
        )
        .build()
}
