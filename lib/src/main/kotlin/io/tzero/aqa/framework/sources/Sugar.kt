package io.tzero.aqa.framework.sources

import io.tzero.aqa.framework.utils.ArraysUtils.toArgumentsStream
import org.junit.jupiter.params.provider.ArgumentsProvider

fun <T> stream(vararg args: T) = stream({ _, it -> it }, args)

fun <T, R> stream(transform: (Int, T) -> R, vararg args: T) = ArgumentsProvider {
    args.toArgumentsStream(transform)
}
