package com.dmalohlovets.tests.config.components

import com.dmalohlovets.tests.config.interfaces.DataInserter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import org.springframework.stereotype.Component
import java.io.FileOutputStream
import java.io.OutputStream

@Component
class RatesFileInserter(private val outputFile: String = "rates.csv") : DataInserter {
    override fun putItem(
        date: String,
        max: String,
        min: String,
        source: String,
    ) = runTest {
        async(Dispatchers.IO) {
            FileOutputStream(outputFile, true).writeCsv(min, max, date, source)
        }
    }

    private fun OutputStream.writeCsv(vararg data: String) =
        bufferedWriter().use {
            it.write(data.joinToString(",", postfix = "\n"))
            it.flush()
        }
}
