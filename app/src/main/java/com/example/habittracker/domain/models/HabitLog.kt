package com.example.habittracker.domain.models

import java.time.LocalDate

data class HabitLog(
    val id: Int,
    val habitId: Int,
    val date: LocalDate,
    val value: Int
)
