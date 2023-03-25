package io.tzero.aqa.framework.api.specs

import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response

object RequestSpec {
    val json =
        RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .build()!!

    fun base(xrftoken: String, tzmCookie: String) =
        RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .addHeaders(
                mapOf(
                    "X-TZM-XSRF-TOKEN" to xrftoken,
                    "Cookie" to "tzm-access=$tzmCookie",
                )
            )
            .build()

    context (ExtractableResponse<Response>)
    fun base() = base(cookie("TZM-XSRF-TOKEN"), cookie("tzm-access"))
}
