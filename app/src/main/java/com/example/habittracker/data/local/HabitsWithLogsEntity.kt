package com.example.habittracker.data.local

import androidx.room3.Embedded
import androidx.room3.Relation
import com.example.habittracker.domain.models.Habit
import java.time.LocalDate
import java.time.LocalTime

data class HabitsWithLogsEntity(
    @Embedded
    val habit: HabitEntity,
    @Relation(
        parentColumns = ["id"],
        entityColumns = ["habit_id"]
    )
    val logs: List<HabitLogEntity>
)

fun HabitsWithLogsEntity.toDomain() = Habit(
    id = habit.id,
    name = habit.name,
    icon = habit.icon,
    target = habit.target,
    unit = habit.unit,
    startDate = LocalDate.parse(habit.startDate),
//    reminderTime = LocalTime.parse(habit.reminderTime),
    frequency = habit.frequency,
    logs = logs.toDomain()
)

fun List<HabitsWithLogsEntity>.toDomain() = map { it.toDomain() }

fun Map<HabitEntity, List<HabitLogEntity>>.toDomain() = map { (habit, logs) ->
    Habit(
        id = habit.id,
        name = habit.name,
        icon = habit.icon,
        target = habit.target,
        unit = habit.unit,
        startDate = LocalDate.parse(habit.startDate),
//        reminderTime = LocalTime.parse(habit.reminderTime),
        frequency = habit.frequency,
        logs = logs.toDomain()
    )
}
