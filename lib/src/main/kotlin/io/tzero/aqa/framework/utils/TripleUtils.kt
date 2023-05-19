package io.tzero.aqa.framework.utils

class TripleUtils {
    infix fun <T, U, S, V> ((T, U, S) -> V).callWith(arguments: Triple<T, U, S>): V =
        this(arguments.first, arguments.second, arguments.third)
}
