package io.tzero.aqa.framework.utils

import org.junit.jupiter.params.provider.Arguments

object ArraysUtils {
    fun <T, R> Array<T>.toArgumentsStream(transform: (T) -> R) = map(transform).map {
        when (it) {
            is Pair<*, *> -> Arguments.of(it.first, it.second)
            else -> Arguments.of(it)
        }
    }.stream()
}
