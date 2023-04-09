package io.tzero.aqa.framework.sources

import io.tzero.aqa.framework.utils.ArraysUtils.toArgumentsStream
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider

fun <T> stream(vararg args: T) = stream({ it }, args)

fun <T, R> stream(transform: (T) -> R, vararg args: T) = ArgumentsProvider {
    args.toArgumentsStream(transform)
}

fun ArgumentsProvider.adapt(vararg addition: Any) = provideArguments(null)
    .map { Arguments.of(*it.get(), *addition) }
