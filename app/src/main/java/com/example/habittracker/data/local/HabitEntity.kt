package com.example.habittracker.data.local

import androidx.room3.ColumnInfo
import androidx.room3.Embedded
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.example.habittracker.domain.models.Frequency
import com.example.habittracker.domain.models.Habit
import com.example.habittracker.domain.models.HabitIcon
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "habits")
data class HabitEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val icon: HabitIcon,
    val target: Int? = null,
    val unit: String?,
    @ColumnInfo(name = "start_date")
    val startDate: String,
    @ColumnInfo(name = "reminder_time")
    val reminderTime: String?,
    @Embedded(prefix = "frequency_")
    val frequency: Frequency,
)

fun HabitEntity.toDomain() = Habit(
    id = id,
    name = name,
    icon = icon,
    target = target,
    unit = unit,
    startDate = LocalDate.parse(startDate),
    // TODO:  modify this to use LocalDate.parse()
    reminderTime = LocalTime.now(),
    frequency = frequency,
)

fun List<HabitEntity>.toDomain() = map { it.toDomain() }

fun Habit.toEntity() = HabitEntity(
    id = id,
    name = name,
    icon = icon,
    target = target,
    unit = unit,
    startDate = startDate.toString(),
    reminderTime = reminderTime.toString(),
    frequency = frequency,
)

fun List<Habit>.toEntity() = map { it.toEntity() }