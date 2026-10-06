package com.example.habittracker.data.local

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey
import com.example.habittracker.domain.models.HabitLog
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity(tableName = "habit_logs",
    foreignKeys = [ForeignKey(HabitEntity::class, ["id"],
        ["habit_id"], onDelete = ForeignKey.CASCADE)])
data class HabitLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "habit_id")
    val habitId: Int,
    val date: String,
    val value: Int
)

fun HabitLogEntity.toDomain() = HabitLog(
    id = id,
    habitId = habitId,
    date = LocalDate.parse(date),
    value = value
)

fun HabitLog.toEntity() = HabitLogEntity(
    id = id,
    habitId = habitId,
    date = date.toString(),
    value = value
)


