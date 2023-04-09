package io.tzero.aqa.framework.sources

import org.apache.commons.lang3.RandomStringUtils

object Data {
    val invalidIds = arrayOf(
        RandomStringUtils.randomAlphanumeric(400),
        "\uFF00",
        RandomStringUtils.random(10),
        RandomStringUtils.randomPrint(10),
        RandomStringUtils.randomGraph(10),
    )

    val spaceAndNonPrinted = arrayOf(" ", "\t", "\n")
}
