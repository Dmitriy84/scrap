package io.tzero.aqa.framework.sources

import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream

inline fun <reified T : Any> arguments(vararg args: T) = ArgumentsProvider {
    Stream.of(*args).map { Arguments.of(it) }
}

class SpaceAndNonPrintedSource : ArgumentsProvider by arguments(" ", "\t", "\n")

fun ArgumentsProvider.adapt(vararg addition: Any) = provideArguments(null)
    .map { Arguments.of(it.get()[0], *addition) }
