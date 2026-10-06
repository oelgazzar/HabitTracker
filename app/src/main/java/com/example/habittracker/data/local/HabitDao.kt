package com.example.habittracker.data.local

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habit_logs")
    fun getHabitLogs(): Flow<List<HabitLogEntity>>

    @Transaction
    @Query("SELECT * FROM habits")
    fun getHabitsWithLogs(): Flow<List<HabitsWithLogsEntity>>

}

