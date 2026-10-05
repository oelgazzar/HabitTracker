package com.example.habittracker.domain.streak

import com.example.habittracker.domain.models.Occurrence
import java.time.DayOfWeek
import java.time.LocalDate

class SpecificDaysPerWeekStreakRule(
    private val days: Set<DayOfWeek>
) : StreakRule {
    override fun isConsecutive(
        previous: Occurrence,
        current: Occurrence
    ): Boolean {
        var next = previous.anchorDate.plusDays(1)

        while (next.dayOfWeek !in days) {
            next = next.plusDays(1)
        }

        return current.anchorDate == next
    }

    override fun isActive(
        lastCompleted: Occurrence,
        today: LocalDate
    ): Boolean {
        var next = lastCompleted.anchorDate.plusDays(1)

        while (next.dayOfWeek !in days) {
            next = next.plusDays(1)
        }

        return today.isAfter(next).not()
    }
}