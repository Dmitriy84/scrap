package io.tzero.aqa.framework.sources

import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider

inline fun <reified T : Any> arguments(vararg args: T) = ArgumentsProvider {
    args.map { Arguments.of(it) }.stream()
}

class SpaceAndNonPrintedSource : ArgumentsProvider by arguments(arrayOf(" ", "\t", "\n"))
