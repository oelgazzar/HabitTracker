package com.example.habittracker.domain

import com.example.habittracker.data.repository.LocalHabitRepository
import com.example.habittracker.domain.models.DayRecord
import com.example.habittracker.domain.models.FrequencyType
import com.example.habittracker.domain.models.Habit
import com.example.habittracker.domain.models.WeekRecord
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

class HabitManager @Inject constructor(
    private val repository: LocalHabitRepository
) {
    fun getAllHabits() = repository.getAllHabits()

    fun getTodayHabitRecords() = repository.getHabitsWithLogs().map {
        extractTodayRecords(it)
    }

    fun extractTodayRecords(habits: List<Habit>): List<DayRecord> {
        val today = LocalDate.now()
        val todayRecords = mutableListOf<DayRecord>()

        for (habit in habits) {
            val weekRecord = getCurrentWeekRecord(habit)
            val todayRecord = weekRecord.dayRecords.firstOrNull { it.anchorDate == today }?:
            DayRecord(habit, today, emptyList())

            if (getTodayStatus(habit, todayRecord, weekRecord) in arrayOf(
                    DayStatus.AVAILABLE,
                    DayStatus.COMPLETED
                )
            ) {
                todayRecords.add(todayRecord)
            }
        }

        return todayRecords
    }

    fun getTodayStatus(habit: Habit, dayRecord: DayRecord, weekRecord: WeekRecord): DayStatus {
        val today = LocalDate.now()
        val freq = habit.frequency

        return when (freq.type) {
            FrequencyType.DAILY, FrequencyType.EVERY_N_DAYS -> {
                println(ChronoUnit.DAYS.between(habit.startDate, today))
                println(habit.frequency.dayInterval)
                when {
                    dayRecord.isSatisfied -> DayStatus.COMPLETED
                    ChronoUnit.DAYS.between(habit.startDate, today) %
                            (freq.dayInterval?:1) == 0L -> DayStatus.AVAILABLE

                    else -> DayStatus.NOT_SCHEDULED_OR_NEEDED
                }
//                DayStatus.AVAILABLE
            }

            FrequencyType.SELECTED_DAYS_PER_WEEK -> {
                when {
                    dayRecord.isSatisfied -> DayStatus.COMPLETED
                    freq.selectedDays?.contains(today.dayOfWeek) == true -> DayStatus.AVAILABLE
                    else -> DayStatus.NOT_SCHEDULED_OR_NEEDED
                }
            }

            FrequencyType.N_DAYS_PER_WEEK -> {
                when {
                    dayRecord.isSatisfied -> DayStatus.COMPLETED
                    !weekRecord.isSatisfied -> DayStatus.AVAILABLE
                    else -> DayStatus.NOT_SCHEDULED_OR_NEEDED
                }
            }
        }
    }

    fun getCurrentWeekRecord(habit: Habit): WeekRecord {
        val today = LocalDate.now()
        val weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY))
        val weekEnd = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY))

        val dayRecords = habit.logs!!.filter {
            it.date in weekStart..weekEnd
        }.groupBy { it.date }
            .map { (date, logs) ->
                DayRecord(habit, date, logs)
            }

        return WeekRecord(habit, weekStart, dayRecords)
    }
}

enum class DayStatus {
    AVAILABLE,
    COMPLETED,
    NOT_SCHEDULED_OR_NEEDED
}