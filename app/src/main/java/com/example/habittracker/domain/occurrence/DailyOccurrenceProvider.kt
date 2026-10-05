package com.example.habittracker.domain.occurrence

import com.example.habittracker.domain.models.HabitLog
import com.example.habittracker.domain.models.Occurrence

class DailyOccurrenceProvider : OccurrenceProvider {
    override fun getOccurrences(logs: List<HabitLog>, target: Int): List<Occurrence> {
        return logs.groupBy {
            it.date
        }
            .map { (anchorDate, logs) ->
                Occurrence.Day(anchorDate, logs, target)
            }
            .sortedBy { it.anchorDate }
    }
}