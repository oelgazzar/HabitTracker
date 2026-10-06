package com.example.habittracker.domain

import com.example.habittracker.data.repository.LocalHabitRepository
import com.example.habittracker.domain.models.DayRecord
import com.example.habittracker.domain.models.FrequencyType
import com.example.habittracker.domain.models.Habit
import com.example.habittracker.domain.models.WeekRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject
import kotlin.math.log

class HabitManager @Inject constructor(
    private val repository: LocalHabitRepository
) {
    fun getAllHabits() = repository.getAllHabits()

    fun getTodayHabitRecords(): Flow<List<DayRecord>> {
        val today = LocalDate.now()
        val weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY))
        val weekEnd = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY))

        return repository.getAllHabits().combine(repository.getHabitLogs(weekStart, weekEnd)) { habits, logs ->
            habits.map { habit ->
                habit.copy(logs = logs.filter { it.habitId == it.id })
            }
        }.map {
            extractTodayRecords(it, today)
        }
    }

    private fun extractTodayRecords(habits: List<Habit>, today: LocalDate): List<DayRecord> {
        return habits.mapNotNull { habit ->
            val weekRecord = getCurrentWeekRecord(habit, today)
            // Create empty one if no logs today
            val todayRecord =
                weekRecord.dayRecords.firstOrNull { it.anchorDate == today } ?: DayRecord(
                    habit,
                    today,
                    emptyList()
                )

            todayRecord.takeIf {
                getTodayStatus(
                    habit,
                    todayRecord,
                    weekRecord,
                    today
                ) != DayStatus.NOT_SCHEDULED_OR_NEEDED
            }
        }
    }

    private fun getTodayStatus(
        habit: Habit,
        dayRecord: DayRecord,
        weekRecord: WeekRecord,
        today: LocalDate
    ): DayStatus {
        val freq = habit.frequency

        return when (freq.type) {
            FrequencyType.DAILY -> {
                when {
                    dayRecord.isSatisfied -> DayStatus.COMPLETED
                    today < habit.startDate -> DayStatus.NOT_SCHEDULED_OR_NEEDED
                    else -> DayStatus.AVAILABLE
                }
            }

            FrequencyType.EVERY_N_DAYS -> {
                when {
                    dayRecord.isSatisfied -> DayStatus.COMPLETED
                    today < habit.startDate -> DayStatus.NOT_SCHEDULED_OR_NEEDED
                    ChronoUnit.DAYS.between(habit.startDate, today) %
                            (freq.dayInterval ?: 1) == 0L -> DayStatus.AVAILABLE

                    else -> DayStatus.NOT_SCHEDULED_OR_NEEDED
                }
//                DayStatus.AVAILABLE
            }

            FrequencyType.SELECTED_DAYS_PER_WEEK -> {
                when {
                    dayRecord.isSatisfied -> DayStatus.COMPLETED
                    today < habit.startDate -> DayStatus.NOT_SCHEDULED_OR_NEEDED
                    freq.selectedDays?.contains(today.dayOfWeek) == true -> DayStatus.AVAILABLE
                    else -> DayStatus.NOT_SCHEDULED_OR_NEEDED
                }
            }

            FrequencyType.N_DAYS_PER_WEEK -> {
                when {
                    dayRecord.isSatisfied -> DayStatus.COMPLETED
                    today < habit.startDate -> DayStatus.NOT_SCHEDULED_OR_NEEDED
                    !weekRecord.isSatisfied -> DayStatus.AVAILABLE
                    else -> DayStatus.NOT_SCHEDULED_OR_NEEDED
                }
            }
        }
    }

    // Return week records from logs, if empty -> create a week record with no day records
    private fun getCurrentWeekRecord(habit: Habit, today: LocalDate): WeekRecord {
        val weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY))
        val weekEnd = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY))

        val dayRecords = habit.logs?.filter {
            it.date in weekStart..weekEnd
        }?.groupBy { it.date }
            ?.map { (date, logs) ->
                DayRecord(habit, date, logs)
            } ?: emptyList()

        return WeekRecord(habit, weekStart, dayRecords)
    }
}

enum class DayStatus {
    AVAILABLE,
    COMPLETED,
    NOT_SCHEDULED_OR_NEEDED
}