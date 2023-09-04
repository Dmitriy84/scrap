package aqa.framework.utils

import org.junit.jupiter.params.provider.Arguments

object ArraysUtils {
    fun <T, R> Array<T>.toArgumentsStream(transform: (Int, T) -> R) = mapIndexed(transform).map {
        when (it) {
            is Pair<*, *> -> Arguments.of(it.first, it.second)
            is Triple<*, *, *> -> Arguments.of(it.first, it.second, it.third)
            else -> Arguments.of(it)
        }
    }.stream()
}
