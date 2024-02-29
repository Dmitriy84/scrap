package com.dmalohlovets.tasks.richtechio

import com.dmalohlovets.tasks.framework.base.BasePlaywrightWebTest
import com.dmalohlovets.tasks.richtechio.pages.Simple7CharValidationPage
import org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import org.apache.commons.lang3.RandomStringUtils.randomNumeric
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.of
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals


class RistTechIOTests : BasePlaywrightWebTest() {
    @BeforeAll
    fun `new page`() {
        simple7CharValidationPage.newPage()
    }

    @ParameterizedTest(name = "\"{0}\" should be \"{1}\" ---> {2}")
    @MethodSource("charactersToTest")
    //TODO Parallelism requires implementation of BeanFactoryPostProcessor for the playwright driver. It is not worth it at the moment
//    @Execution(ExecutionMode.CONCURRENT)
    fun `Only A-Z, a-z, 0-9, and * are valid characters`(input: String?, expected: String, description: String) {
        with(simple7CharValidationPage) {
            navigate()
            page.locator(characters).fill(input)
            page.locator(validate).click()
            assertEquals(expected, page.locator(message).evaluate("el => el.value"))
        }
    }

    @Autowired
    private lateinit var simple7CharValidationPage: Simple7CharValidationPage

    companion object {
        private const val INVALID = "Invalid Value"
        private const val VALID = "Valid Value"

        // 42       *
        // 48 - 57  0 - 9
        // 65 - 90  A - Z
        // 97 - 122 a - z
        @JvmStatic
        private fun charactersToTest() = listOf(
            of(randomAlphanumeric(7), VALID, " 7 chars in range [0_9a_zA_Z]"),
            of(randomNumeric(7), VALID, "7 chars in range [0_9]"),
            of(randomAlphabetic(7), VALID, "7 chars in range [a_zA_Z]"),
            of(randomAlphabetic(6) + "*", VALID, "6 chars in range [a_zA_Z] and *"),
            of("*".repeat(7), VALID, "7 * chars"),

            of("", INVALID, "empty"),
            of(" ".repeat(7), INVALID, "only spaces"),
            of(
                randomAlphanumeric(6) + (Char.MIN_VALUE.code..41).random().toChar(),
                INVALID,
                "has 1 random char from ascii codes [0-41]"
            ),
            of(
                randomAlphanumeric(6) + (43..47).random().toChar(),
                INVALID,
                "has 1 random char from ascii codes [43-47]"
            ),
            of(
                randomAlphanumeric(6) + (58..64).random().toChar(),
                INVALID,
                "has 1 random char from ascii codes [58-64]"
            ),
            of(
                randomAlphanumeric(6) + (91..96).random().toChar(),
                INVALID,
                "has 1 random char from ascii codes [91-96]"
            ),
            of(
                randomAlphanumeric(6) + (123..Char.MAX_VALUE.code).random().toChar(),
                INVALID,
                "has 1 random char from ascii codes [123-Char.MAX_VALUE]"
            ),

            // TODO BUGs in application
            of("_".repeat(7), INVALID, "_ character is not allowed"),
            of("`".repeat(7), INVALID, "` character is not allowed"),
            of("\\".repeat(7), INVALID, "\\ character is not allowed"),
            of("^".repeat(7), INVALID, "^ character is not allowed"),
            of("]".repeat(7), INVALID, "] character is not allowed"),
            of("[".repeat(7), INVALID, "[ character is not allowed"),
            of(":".repeat(7), INVALID, ": character is not allowed"),

            of(randomAlphanumeric((1..6).random()), INVALID, "length of a string is less than 7"),
            // TODO Caused by: java.lang.OutOfMemoryError: Java heap space
//                of(randomAlphanumeric((8..Int.MAX_VALUE).random()), INVALID, "length of a string is bigger than 7"),
            of(randomAlphanumeric((8..1000).random()), INVALID, "length of a string is bigger than 7"),
        )
    }
}
