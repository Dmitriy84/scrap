package com.dmalohlovets.tasks

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Jfrog {
    @ParameterizedTest
    @CsvSource(
        value = ["frog:fag", "frog:fog", "frog:frogs", "frog:from", "frog:monkey", "frog:flag"],
        delimiter = ':',
    )
    fun compareStrings(
        first: String,
        second: String,
    ) {
        val diffObj = DiffMatchPatch()
        val diffs = diffObj.diffMain(first, second)
        assert(diffObj.diffLevenshtein(diffs) <= 1)
    }
}
