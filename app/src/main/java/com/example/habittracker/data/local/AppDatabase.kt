package com.example.habittracker.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(entities = [HabitEntity::class, HabitLogEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}