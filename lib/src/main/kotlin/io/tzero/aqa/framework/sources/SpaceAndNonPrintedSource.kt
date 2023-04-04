package io.tzero.aqa.framework.sources

import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream

inline fun <reified T : Any> arguments(vararg args: T) = ArgumentsProvider {
    Stream.of(*args).map { Arguments.of(it) }
}

class SpaceAndNonPrintedSource : ArgumentsProvider by arguments(arrayOf(" ", "\t", "\n"))
