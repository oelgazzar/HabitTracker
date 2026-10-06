package com.example.habittracker.domain.models

import java.time.LocalDate

data class Day(
    override val anchorDate: LocalDate,
    val logs: List<HabitLog>,
    override val target: Int,
) : Record {
    override val progress: Int
        get() = logs.sumOf { it.value }
}

fun List<HabitLog>.toDays(dailyTarget: Int) = groupBy {
    it.date
}.map {
    Day(it.key, it.value, dailyTarget)
}