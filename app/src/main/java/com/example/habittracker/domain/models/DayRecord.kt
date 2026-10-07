package com.example.habittracker.domain.models

import java.time.LocalDate

data class DayRecord(
    override val habit: Habit,
    override val anchorDate: LocalDate,
    val logs: List<HabitLog>,
) : Record {
    override val progress: Int
        get() = logs.sumOf { it.value }.coerceAtMost(target)
    override val target: Int
        get() = habit.target?:1
}

fun List<HabitLog>.toDayRecords(habit: Habit) = groupBy {
    it.date
}.map {
    DayRecord(habit, it.key, it.value)
}

fun Habit.toDayRecord(day: LocalDate) = DayRecord(
    this,
    day,
    this.logs?.filter { day == it.date }?:emptyList())