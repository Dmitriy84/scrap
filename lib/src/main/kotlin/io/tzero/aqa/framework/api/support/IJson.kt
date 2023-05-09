package io.tzero.aqa.framework.api.support

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface IJson {
    @OptIn(ExperimentalSerializationApi::class)
    val json
        get() =
            Json {
                encodeDefaults = true
                isLenient = true
                explicitNulls = false
            }

    fun toJson() = json.encodeToString(this)
}
