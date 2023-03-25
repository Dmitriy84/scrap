package io.tzero.aqa.framework.api.utils

import org.apache.commons.lang3.RandomStringUtils

object StringUtils {
    fun generateRandomEmail(numberOfChars: Int = 16) =
        RandomStringUtils.randomAlphanumeric(numberOfChars) + "@tzero.com"
}
