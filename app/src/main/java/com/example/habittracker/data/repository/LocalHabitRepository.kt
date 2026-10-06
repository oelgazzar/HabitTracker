package com.example.habittracker.data.repository

import com.example.habittracker.data.local.HabitDao
import com.example.habittracker.data.local.toDomain
import jakarta.inject.Inject
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class LocalHabitRepository @Inject constructor(
    private val habitDao: HabitDao
) {
    fun getAllHabits() = habitDao.getAllHabits().map { it.toDomain() }

    fun getHabitLogs(startDate: LocalDate, endDate: LocalDate) = habitDao.getHabitLogs(
        startDate, endDate
    ).map { it.toDomain() }

    fun getHabitsWithLogs(startDate: LocalDate, endDate: LocalDate) = habitDao.getHabitsWithLogs(
        startDate, endDate
    ).map { it.toDomain() }
}