package com.example.habittracker.domain.models

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

data class WeekRecord(
    override val habit: Habit,
    override val anchorDate: LocalDate,
    val dayRecords: List<DayRecord>,
) : Record {
    override val progress: Int
        get() = dayRecords.sumOf { if (it.isSatisfied) 1 else 0 }
    override val target: Int
        get() = habit.frequency.nDaysPerWeek?:1
}

fun List<DayRecord>.toWeekRecords(habit: Habit) = groupBy {
    it.anchorDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY))
}.map {
    WeekRecord(habit, it.key, it.value)
}