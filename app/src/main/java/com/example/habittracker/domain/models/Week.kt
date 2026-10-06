package com.example.habittracker.domain.models

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

data class Week(
    override val anchorDate: LocalDate,
    val days: List<Day>,
    override val target: Int
) : Record {
    override val progress: Int
        get() = days.sumOf { if (it.isSatisfied) 1 else 0 }
}

fun List<Day>.toWeeks(daysPerWeek: Int) = groupBy {
    it.anchorDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY))
}.map {
    Week(it.key, it.value, daysPerWeek)
}