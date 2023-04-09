package io.tzero.aqa.framework.sources

import io.tzero.aqa.framework.utils.ArraysUtils.toArgumentsStream
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider

inline fun <reified T : Any> stream(vararg args: T) = ArgumentsProvider {
    args.toArgumentsStream()
}

fun ArgumentsProvider.adapt(vararg addition: Any) = provideArguments(null)
    .map { Arguments.of(*it.get(), *addition) }
