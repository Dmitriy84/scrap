package io.tzero.aqa.framework.utils

import org.junit.jupiter.params.provider.Arguments

object ArraysUtils {
    fun <T, R> Array<T>.toArgumentsStream(transform: (Int, T) -> R) = mapIndexed(transform).map {
        when (it) {
            is Pair<*, *> -> Arguments.of(*it.toList().toTypedArray())
            is Triple<*, *, *> -> Arguments.of(*it.toList().toTypedArray())
            else -> Arguments.of(it)
        }
    }.stream()
}
