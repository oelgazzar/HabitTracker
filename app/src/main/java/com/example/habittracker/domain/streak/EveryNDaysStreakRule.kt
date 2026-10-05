package com.example.habittracker.domain.streak

import com.example.habittracker.domain.models.Occurrence
import java.time.LocalDate

open class EveryNDaysStreakRule(
    private val days: Long
) : StreakRule {

    override fun isConsecutive(
        previous: Occurrence,
        current: Occurrence
    ): Boolean {
        println(previous)
        println(current)
        return current.anchorDate == previous.anchorDate.plusDays(days)
    }

    override fun isActive(lastCompleted: Occurrence, today: LocalDate): Boolean {
        return today.isAfter(lastCompleted.anchorDate.plusDays(days)).not()
    }
}