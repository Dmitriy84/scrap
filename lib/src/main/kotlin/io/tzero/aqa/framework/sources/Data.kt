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

    val invalidEmail = arrayOf(
        "${RandomStringUtils.randomAlphanumeric(90)}@${RandomStringUtils.randomAlphanumeric(10)}.ca", //exceed max chars >100
        "", //empty
        null, //"null"
        "${RandomStringUtils.randomAlphanumeric(10)}.${RandomStringUtils.randomAlphanumeric(10)}.me", //@ missing
        "${RandomStringUtils.randomAlphabetic(1)}@${RandomStringUtils.randomAlphabetic(1)}", //minimum 3 chars https://stackoverflow.com/questions/1423195/what-is-the-actual-minimum-length-of-an-email-address-as-defined-by-the-ietf#:~:text=Contemporary%20email%20addresses%20consist%20of,three%20characters%20is%20the%20shortest.
        "@${RandomStringUtils.randomAlphabetic(1)}.co", //missing name
        "${RandomStringUtils.randomAlphanumeric(1)}@${RandomStringUtils.randomAlphanumeric(1)}.io.", //trailing dot
        ".${RandomStringUtils.randomAlphanumeric(1)}@${RandomStringUtils.randomAlphanumeric(1)}.org", //leading dot
        "${RandomStringUtils.randomAlphabetic(2)}@${RandomStringUtils.randomAlphabetic(2)}\uFF00", //unicode symbol
        "${RandomStringUtils.randomAlphabetic(2)}@${RandomStringUtils.randomAlphabetic(2)}@${
            RandomStringUtils.randomAlphabetic(
                2
            )
        }.com",//multiple @
        "${RandomStringUtils.randomAlphabetic(4, 10)} ${
            RandomStringUtils.randomAlphabetic(
                4,
                10
            )
        } <${RandomStringUtils.randomAlphabetic(4, 10)}@${
            RandomStringUtils.randomAlphabetic(4, 10)
        }@${RandomStringUtils.randomAlphabetic(2, 6)}.com>",//like John Doe <example@email.com>
        "${RandomStringUtils.randomAlphabetic(4, 10)}.@${
            RandomStringUtils.randomAlphabetic(
                4,
                10
            )
        }.net",//like email.@domain.com
        "あいうえお@${RandomStringUtils.randomAlphabetic(4, 10)}.com",//like あいうえお@domain.com
        "${RandomStringUtils.randomAlphabetic(4, 10)}@${
            RandomStringUtils.randomAlphabetic(
                4,
                10
            )
        }.ua (${RandomStringUtils.randomAlphabetic(4, 10)} ${
            RandomStringUtils.randomAlphabetic(4, 10)
        })",//like email@domain.com (Joe Smith)
        "${RandomStringUtils.randomAlphabetic(4, 10)}@-${
            RandomStringUtils.randomAlphabetic(
                4,
                10
            )
        }.us",//like email@-domain.com
        "${RandomStringUtils.randomAlphabetic(4, 10)}@${
            RandomStringUtils.randomAlphabetic(
                4,
                10
            )
        }.web",//like email@domain.web (web is not correct)
        "${
            RandomStringUtils.randomAlphabetic(4, 10)
        }@${RandomStringUtils.randomNumeric(3)}.${RandomStringUtils.randomNumeric(3)}.${
            RandomStringUtils.randomNumeric(
                3
            )
        }.${RandomStringUtils.randomNumeric(4)}",//like email@111.222.333.44444 (invalid ip)
        "${RandomStringUtils.randomAlphabetic(4, 10)}@${
            RandomStringUtils.randomAlphabetic(
                4,
                10
            )
        }..uk",//like email@domain..com
    )
}
