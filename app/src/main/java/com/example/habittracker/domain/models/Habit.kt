package com.example.habittracker.domain.models

data class Habit(
    val id: Int,
    val name: String,
    val frequency: Frequency,
    val targetAmount: Int?
)