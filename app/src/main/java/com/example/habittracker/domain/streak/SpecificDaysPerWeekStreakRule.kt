package com.example.habittracker.domain.streak

import com.example.habittracker.domain.models.Record
import java.time.DayOfWeek
import java.time.LocalDate

class SpecificDaysPerWeekStreakRule(
    private val days: Set<DayOfWeek>
) : StreakRule {
    override fun isConsecutive(
        previous: Record,
        current: Record
    ): Boolean {
        var next = previous.anchorDate.plusDays(1)

        while (next.dayOfWeek !in days) {
            next = next.plusDays(1)
        }

        return current.anchorDate == next
    }

    override fun isActive(
        lastCompleted: Record,
        today: LocalDate
    ): Boolean {
        var next = lastCompleted.anchorDate.plusDays(1)

        while (next.dayOfWeek !in days) {
            next = next.plusDays(1)
        }

        return today.isAfter(next).not()
    }

    override fun countsTowardsStreakCalculation(record: Record) =
        record.anchorDate.dayOfWeek in days
}