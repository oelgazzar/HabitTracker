package com.example.habittracker.domain

import com.example.habittracker.domain.models.DayRecord
import com.example.habittracker.domain.models.FrequencyType
import com.example.habittracker.domain.models.Habit
import com.example.habittracker.domain.models.WeekRecord
import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun getStatusForDate(
    habit: Habit,
    dayRecord: DayRecord,
    weekRecord: WeekRecord,
    date: LocalDate
): DayStatus {
    val freq = habit.frequency

    return when (freq.type) {
        FrequencyType.DAILY -> {
            when {
                dayRecord.isSatisfied -> DayStatus.COMPLETED
                date < habit.startDate -> DayStatus.NOT_SCHEDULED_OR_NEEDED
                else -> DayStatus.AVAILABLE
            }
        }

        FrequencyType.EVERY_N_DAYS -> {
            when {
                dayRecord.isSatisfied -> DayStatus.COMPLETED
                date < habit.startDate -> DayStatus.NOT_SCHEDULED_OR_NEEDED
                ChronoUnit.DAYS.between(habit.startDate, date) %
                        (freq.dayInterval ?: 1) == 0L -> DayStatus.AVAILABLE

                else -> DayStatus.NOT_SCHEDULED_OR_NEEDED
            }
//                DayStatus.AVAILABLE
        }

        FrequencyType.SELECTED_DAYS_PER_WEEK -> {
            when {
                dayRecord.isSatisfied -> DayStatus.COMPLETED
                date < habit.startDate -> DayStatus.NOT_SCHEDULED_OR_NEEDED
                freq.selectedDays?.contains(date.dayOfWeek) == true -> DayStatus.AVAILABLE
                else -> DayStatus.NOT_SCHEDULED_OR_NEEDED
            }
        }

        FrequencyType.N_DAYS_PER_WEEK -> {
            when {
                dayRecord.isSatisfied -> DayStatus.COMPLETED
                date < habit.startDate -> DayStatus.NOT_SCHEDULED_OR_NEEDED
                !weekRecord.isSatisfied -> DayStatus.AVAILABLE
                else -> DayStatus.NOT_SCHEDULED_OR_NEEDED
            }
        }
    }
}