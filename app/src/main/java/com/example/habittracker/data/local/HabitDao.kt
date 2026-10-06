package com.example.habittracker.data.local

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habit_logs WHERE date BETWEEN :startDate AND :endDate")
    fun getHabitLogs(startDate: LocalDate, endDate: LocalDate): Flow<List<HabitLogEntity>>

    @Query("SELECT * FROM habits JOIN habit_logs ON habits.id = habit_logs.habit_id WHERE habit_logs.date BETWEEN :startDate AND :endDate ")
    fun getHabitsWithLogs(startDate: LocalDate, endDate: LocalDate): Flow<Map<HabitEntity, List<HabitLogEntity>>>

}

