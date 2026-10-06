package com.example.habittracker.data.local

import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.habittracker.domain.models.TypeConverter

@Database(entities = [HabitEntity::class, HabitLogEntity::class], version = 1)
@ColumnTypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}