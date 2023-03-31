package io.tzero.aqa.framework.utils

import org.junit.jupiter.params.provider.Arguments

object ArraysUtils {
    fun <T> Array<T>.toArgumentsStream() = map {
        Arguments.of(
            when (it) {
                is Pair<*, *> -> arrayOf(it.first, it.second)
                else -> it.toString()
            }
        )
    }.stream()
}
