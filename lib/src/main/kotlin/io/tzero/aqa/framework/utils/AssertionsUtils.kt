package io.tzero.aqa.framework.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson
import net.javacrumbs.jsonunit.core.Option

object AssertionsUtils {
    inline infix fun <reified T> String.shouldBeJson(expected: T) =
        assertThatJson(this)
            .`when`(Option.IGNORING_ARRAY_ORDER)
            .isEqualTo(Json.encodeToString(expected))
}
