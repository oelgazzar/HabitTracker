package com.example.habittracker.domain.streak

import com.example.habittracker.domain.models.Record
import java.time.LocalDate

interface StreakRule {
    fun isConsecutive(
        previous: Record,
        current: Record
    ): Boolean

    fun isActive(
        lastCompleted: Record,
        today: LocalDate
    ): Boolean

    fun countsTowardsStreakCalculation(record: Record): Boolean
}