package io.tzero.aqa.framework.api.support

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Transient
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

interface IJson {
    @OptIn(ExperimentalSerializationApi::class)
    @Transient
    val json
        get() =
            Json {
                encodeDefaults = true
                isLenient = true
                explicitNulls = false
                serializersModule = module()
            }

    fun module(): SerializersModule

    fun toJson() = json.encodeToString(this)
}
