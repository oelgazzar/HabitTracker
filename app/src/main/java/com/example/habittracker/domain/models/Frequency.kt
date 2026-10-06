package com.example.habittracker.domain.models

import androidx.room3.ColumnInfo
import androidx.room3.ColumnTypeConverter
import java.time.DayOfWeek

enum class FrequencyType {
    DAILY,
    EVERY_N_DAYS,
    N_DAYS_PER_WEEK,
    SELECTED_DAYS_PER_WEEK
}

data class Frequency(
    val type: FrequencyType,
    @ColumnInfo(name = "day_interval")
    val dayInterval: Int? = null,
    @ColumnInfo(name = "n_days_per_week")
    val nDaysPerWeek: Int? = null,
    @ColumnInfo(name = "selected_days")
    val selectedDays: List<DayOfWeek>? = null
)

object DaysTypeConverter {
    @ColumnTypeConverter
    fun daysToString(days: List<DayOfWeek>?): String? {
        return days?.joinToString(",") { it.value.toString() }
    }
    @ColumnTypeConverter
    fun daysFromString(days: String?): List<DayOfWeek>? {
        return days?.split(",")?.map { DayOfWeek.of(it.toInt()) }
    }
}