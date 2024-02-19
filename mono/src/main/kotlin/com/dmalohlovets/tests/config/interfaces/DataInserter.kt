package com.dmalohlovets.tests.config.interfaces

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor


interface DataInserter {
    fun putItem(
        date: String = "date",
        max: String = "max",
        min: String = "min",
        source: String = "money24"
    )

    companion object {
        fun dateOf(value: TemporalAccessor = LocalDateTime.now()): String =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(value)

        fun batchProcessor(
            date: String,
            max: String,
            min: String,
            source: String,
            vararg args: DataInserter
        ) = args.forEach {
            it.putItem(date, max, min, source)
        }
    }
}
