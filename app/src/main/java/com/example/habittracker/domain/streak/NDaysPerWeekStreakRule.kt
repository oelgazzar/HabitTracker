package com.example.habittracker.domain.streak

import com.example.habittracker.domain.models.Record
import java.time.LocalDate

class NDaysPerWeekStreakRule : StreakRule {
    override fun isConsecutive(previous: Record, current: Record): Boolean {
        println(previous)
        println(current)
        return current.anchorDate == previous.anchorDate.plusWeeks(1)
    }

    override fun isActive(lastCompleted: Record, today: LocalDate): Boolean {
        return today.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.SATURDAY))
            .isAfter(lastCompleted.anchorDate.plusWeeks(1)).not()
    }

    override fun countsTowardsStreakCalculation(record: Record) = true
}