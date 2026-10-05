package com.example.habittracker.domain.streak

import com.example.habittracker.domain.models.Occurrence
import java.time.LocalDate

interface StreakRule {
    fun isConsecutive(
        previous: Occurrence,
        current: Occurrence
    ): Boolean

    fun isActive(
        lastCompleted: Occurrence,
        today: LocalDate
    ): Boolean
}