package com.example.habittracker.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.example.habittracker.domain.models.Frequency
import com.example.habittracker.domain.models.Habit

@Entity(tableName = "habits")
data class HabitEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val frequency: Frequency,
    val target: Int? = null
)

fun HabitEntity.toDomain() = Habit(
    id = id,
    name = name,
    frequency = frequency,
    target = target
)

fun List<HabitEntity>.toDomain() = map { it.toDomain() }

fun Habit.toEntity() = HabitEntity(
    id = id,
    name = name,
    frequency = frequency,
    target = target
)

fun List<Habit>.toEntity() = map { it.toEntity() }