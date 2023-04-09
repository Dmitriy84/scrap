package io.tzero.aqa.framework.sources

import io.tzero.aqa.framework.utils.ArraysUtils.toArgumentsStream
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider

inline fun <reified T : Any> stream(vararg args: T) = stream({ it }, args)

inline fun <reified T : Any, R> stream(noinline transform: (T) -> R, vararg args: T) = ArgumentsProvider {
    args.toArgumentsStream(transform)
}

fun ArgumentsProvider.adapt(vararg addition: Any) = provideArguments(null)
    .map { Arguments.of(*it.get(), *addition) }
