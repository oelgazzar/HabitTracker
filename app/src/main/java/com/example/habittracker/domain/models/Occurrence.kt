package com.example.habittracker.domain.models

import java.time.LocalDate

sealed interface Occurrence {
    val anchorDate: LocalDate
    val logs: List<HabitLog>
    val target: Int
    val progress: Int
    val isCompleted: Boolean
        get() = progress >= target

    data class Day(override val anchorDate: LocalDate, override val logs: List<HabitLog>,
                   override val target: Int): Occurrence {
        override val progress: Int
            get() = logs.sumOf { it.amount }
    }
    data class Week(override val anchorDate: LocalDate, override val logs: List<HabitLog>,
                    override val target: Int): Occurrence {
        override val progress: Int
            get() = logs.sumOf { it.amount }
    }
}