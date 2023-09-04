package aqa.framework.sources

import aqa.framework.utils.ArraysUtils.toArgumentsStream
import org.junit.jupiter.params.provider.ArgumentsProvider

fun <T : Any?> stream(transform: (Int, T) -> Any? = { _, it -> it }, vararg args: T) =
    ArgumentsProvider { args.toArgumentsStream(transform) }
