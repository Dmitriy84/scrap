package io.tzero.aqa.framework.utils

import com.google.gson.Gson
import net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson
import net.javacrumbs.jsonunit.core.Option

object AssertionsUtils {
    infix fun String.shouldBeJson(expected: Any) =
        assertThatJson(this)
            .`when`(Option.IGNORING_ARRAY_ORDER)
            .isEqualTo(Gson().toJson(expected))
}
