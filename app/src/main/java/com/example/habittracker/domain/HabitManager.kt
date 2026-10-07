package com.example.habittracker.domain

import com.example.habittracker.data.repository.LocalHabitRepository
import com.example.habittracker.domain.models.DayRecord
import com.example.habittracker.domain.models.Habit
import com.example.habittracker.domain.models.WeekRecord
import com.example.habittracker.domain.models.toDayRecord
import com.example.habittracker.domain.models.toDayRecords
import com.example.habittracker.domain.models.toWeekRecords
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

class HabitManager @Inject constructor(
    private val repository: LocalHabitRepository
) {
    fun getAllHabits() = repository.getAllHabits()

    fun getTodayHabitRecords(): Flow<List<DayRecord>> {
        val today = LocalDate.now().minusDays(1)
        val weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY))
        val weekEnd = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY))

        // Attach logs to habit objects
        return combine(
            repository.getAllHabits(),
            repository.getHabitLogs(weekStart, weekEnd)
        ) { habits, logs ->
            habits.map { habit ->
                habit.copy(logs = logs.filter { log -> log.habitId == habit.id })
            }
        }.map { habits ->
            generateTodayRecords(habits, today)
        }
    }

    private fun generateTodayRecords(habits: List<Habit>, today: LocalDate) =
        habits.mapNotNull { habit ->
            val currentWeekDayRecords = habit.logs?.toDayRecords(habit)
            val weekRecord = currentWeekDayRecords?.toWeekRecords(habit)?.firstOrNull() ?: WeekRecord(
                habit,
                today,
                emptyList()
            )

            val todayRecord = habit.toDayRecord(today)

            todayRecord.takeIf {
                getStatusForDate(
                    habit,
                    todayRecord,
                    weekRecord,
                    today
                ) != DayStatus.NOT_SCHEDULED_OR_NEEDED
            }
        }
}