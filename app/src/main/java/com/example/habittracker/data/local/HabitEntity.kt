package com.example.habittracker.data.local

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.example.habittracker.domain.models.Frequency
import com.example.habittracker.domain.models.Habit
import java.time.LocalDate

@Entity(tableName = "habits")
data class HabitEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    @ColumnInfo(name = "start_date")
    val startDate: String,
    val frequency: Frequency,
    val target: Int? = null
)

fun HabitEntity.toDomain() = Habit(
    id = id,
    name = name,
    startDate = LocalDate.parse(startDate),
    frequency = frequency,
    target = target
)

fun List<HabitEntity>.toDomain() = map { it.toDomain() }

fun Habit.toEntity() = HabitEntity(
    id = id,
    name = name,
    startDate = startDate.toString(),
    frequency = frequency,
    target = target
)

fun List<Habit>.toEntity() = map { it.toEntity() }