package io.tzero.aqa.framework.api.support

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Transient
import kotlinx.serialization.json.Json

interface IJson {
    @OptIn(ExperimentalSerializationApi::class)
    @Transient
    val json
        get() =
            Json {
                encodeDefaults = true
                isLenient = true
                explicitNulls = false
            }

    fun toJson(): String
}
