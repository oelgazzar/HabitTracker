package com.example.habittracker.domain.models

import java.time.LocalDate

data class Habit(
    val id: Int,
    val name: String,
    val startDate: LocalDate,
    val frequency: Frequency,
    val target: Int?
)