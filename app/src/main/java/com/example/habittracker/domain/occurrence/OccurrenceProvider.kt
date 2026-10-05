package com.example.habittracker.domain.occurrence

import com.example.habittracker.domain.models.HabitLog
import com.example.habittracker.domain.models.Occurrence

interface OccurrenceProvider {
    fun getOccurrences(logs: List<HabitLog>, target: Int): List<Occurrence>
}

