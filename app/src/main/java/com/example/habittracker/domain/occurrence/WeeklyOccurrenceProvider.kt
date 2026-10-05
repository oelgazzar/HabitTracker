package com.example.habittracker.domain.occurrence

import com.example.habittracker.domain.models.HabitLog
import com.example.habittracker.domain.models.Occurrence
import java.time.DayOfWeek
import java.time.temporal.TemporalAdjusters

class WeeklyOccurrenceProvider : OccurrenceProvider {
    override fun getOccurrences(logs: List<HabitLog>, target: Int): List<Occurrence> {
        return logs.groupBy {
            it.date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY))
        }
            .map { (anchorDate, logs) ->
                Occurrence.Day(anchorDate, logs, target)
            }
            .sortedBy { it.anchorDate }
    }
}