package io.tzero.aqa.framework.api.support

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.longOrNull
import kotlinx.serialization.serializer

object KotlinxGenericMapSerializer : KSerializer<Map<String, Any?>> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("GenericMap")

    override fun serialize(encoder: Encoder, value: Map<String, Any?>) =
        encoder.serializersModule.serializer<JsonObject>()
            .serialize(encoder, JsonObject(value.mapValues { it.value.toJsonElement() }))

    override fun deserialize(decoder: Decoder): Map<String, Any?> {
        val jsonDecoder =
            decoder as? JsonDecoder ?: throw SerializationException("Can only deserialize Json content to generic Map")
        val root = jsonDecoder.decodeJsonElement()
        return if (root is JsonObject) root.toMap() else throw SerializationException("Cannot deserialize Json content to generic Map")
    }

    private fun Any?.toJsonElement(): JsonElement = when (this) {
        null -> JsonNull
        is String -> JsonPrimitive(this)
        is Number -> JsonPrimitive(this)
        is Boolean -> JsonPrimitive(this)
        is Map<*, *> -> toJsonObject()
        is Iterable<*> -> toJsonArray()
        else -> throw SerializationException("Cannot serialize value type $this")
    }

    private fun Map<*, *>.toJsonObject() =
        JsonObject(entries.associate { it.key.toString() to it.value.toJsonElement() })

    private fun Iterable<*>.toJsonArray() = JsonArray(map { it.toJsonElement() })

    private fun JsonElement.toAnyNullableValue() = when (this) {
        is JsonPrimitive -> toScalarOrNull()
        is JsonObject -> toMap()
        is JsonArray -> toList()
    }

    private fun JsonObject.toMap(): Map<String, Any?> = entries.associate {
        when (val jsonElement = it.value) {
            is JsonPrimitive -> it.key to jsonElement.toScalarOrNull()
            is JsonObject -> it.key to jsonElement.toMap()
            is JsonArray -> it.key to jsonElement.toAnyNullableValueList()
        }
    }

    private fun JsonPrimitive.toScalarOrNull() = when {
        this is JsonNull -> null
        this.isString -> content
        else -> listOfNotNull(booleanOrNull, longOrNull, doubleOrNull).firstOrNull()
    }

    private fun JsonArray.toAnyNullableValueList() = map { it.toAnyNullableValue() }
}
