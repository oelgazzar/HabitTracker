package com.example.habittracker.domain.streak

import com.example.habittracker.domain.models.Occurrence
import java.time.LocalDate

class NDaysPerWeekStreakRule : StreakRule {
    override fun isConsecutive(previous: Occurrence, current: Occurrence): Boolean {
        println(previous)
        println(current)
        return current.anchorDate == previous.anchorDate.plusWeeks(1)
    }

    override fun isActive(lastCompleted: Occurrence, today: LocalDate): Boolean {
        return today.isAfter(lastCompleted.anchorDate.plusWeeks(1)).not()
    }

}