package io.tzero.aqa.framework.api.support

import net.javacrumbs.jsonunit.assertj.JsonAssertions

interface IJsonUnitConfig {
    fun ignorePaths(): Array<String>
}

fun String.toBe(expected: String, vararg ignore: String) {
    JsonAssertions.assertThatJson(this)
        .whenIgnoringPaths(*ignore)
        .isEqualTo(expected)
}
