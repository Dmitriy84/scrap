package aqa.framework.utils

import org.apache.commons.lang3.RandomStringUtils

object StringUtils {
    fun generateRandomEmail(numberOfChars: Int = 16) =
        RandomStringUtils.randomAlphanumeric(numberOfChars) + "@tzero.com"

    fun Any.wrap() = toString().let { "\"$it\"" }
}
