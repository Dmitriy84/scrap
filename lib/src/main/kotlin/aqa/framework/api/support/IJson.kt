package aqa.framework.api.support

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Transient
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
                serializersModule = serialize()
            }

    fun serialize(): SerializersModule
}
