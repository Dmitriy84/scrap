package io.tzero.aqa.framework.utils

import io.restassured.module.kotlin.extensions.Extract
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import io.restassured.response.ValidatableResponse
import io.tzero.aqa.framework.api.specs.RequestSpec

object SpecUtils {
    fun ValidatableResponse.toSpec() = Extract { RequestSpec.base() }

    fun ValidatableResponse.toBody() = Extract { asString() }

    fun ValidatableResponse.extractJsonValues(vararg paths: String) = paths.map(extract().jsonPath()::getString)

    context (ExtractableResponse<Response>)
    fun String.toJsonValue() = jsonPath().getString(this)
}
