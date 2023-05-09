package io.tzero.aqa.framework.utils

import io.tzero.aqa.framework.api.support.IJson
import org.apache.commons.lang3.RandomStringUtils

object StringUtils {
    fun generateRandomEmail(numberOfChars: Int = 16) =
        RandomStringUtils.randomAlphanumeric(numberOfChars) + "@tzero.com"

    fun Any.wrap() = toString().let { "\"$it\"" }

    fun Any.toBody() =
        when (this) {
            is IJson -> toJson()
            else -> toString().trimIndent()
        }
}
