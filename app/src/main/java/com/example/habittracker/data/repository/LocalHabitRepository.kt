package com.example.habittracker.data.repository

import com.example.habittracker.data.local.HabitDao
import com.example.habittracker.data.local.toDomain
import jakarta.inject.Inject
import kotlinx.coroutines.flow.map

class LocalHabitRepository @Inject constructor(
    private val habitDao: HabitDao
) {
    fun getAllHabits() = habitDao.getAllHabits().map { it.toDomain() }

    fun getHabitLogs() = habitDao.getHabitLogs().map { it.toDomain() }

    fun getHabitsWithLogs() = habitDao.getHabitsWithLogs().map { it.toDomain() }
}