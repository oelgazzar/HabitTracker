package com.example.habittracker.domain.streak

import com.example.habittracker.domain.models.Record
import java.time.LocalDate

open class EveryNDaysStreakRule(
    private val days: Long
) : StreakRule {

    override fun isConsecutive(
        previous: Record,
        current: Record
    ): Boolean {
        println(previous)
        println(current)
        return current.anchorDate == previous.anchorDate.plusDays(days)
    }

    override fun isActive(lastCompleted: Record, today: LocalDate): Boolean {
        return today.isAfter(lastCompleted.anchorDate.plusDays(days)).not()
    }

    override fun countsTowardsStreakCalculation(record: Record) = true
}