package io.tzero.aqa.accounts.support

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.javacrumbs.jsonunit.assertj.JsonAssertions

interface IJsonUnitConfig {
    fun ignorePaths(): Array<String>
}

inline infix fun <reified T : IJsonUnitConfig> T.toBe(expected: T) {
    val json = Json {
        encodeDefaults = true
        isLenient = true
    }

    JsonAssertions.assertThatJson(json.encodeToString(this))
        .whenIgnoringPaths(*expected.ignorePaths())
        .isEqualTo(json.encodeToString(expected))
}
