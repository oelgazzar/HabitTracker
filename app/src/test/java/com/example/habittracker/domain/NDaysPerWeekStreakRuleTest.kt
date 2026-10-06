package com.example.habittracker.domain

import com.example.habittracker.domain.models.HabitLog
import com.example.habittracker.domain.models.toDays
import com.example.habittracker.domain.models.toWeeks
import com.example.habittracker.domain.streak.NDaysPerWeekStreakRule
import com.example.habittracker.domain.streak.calculateStreak
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.LocalDate

class NDaysPerWeekStreakRuleTest {
    @Test
    fun empty_records_returns_zero_streak() {
        val result = calculateStreak(emptyList(), NDaysPerWeekStreakRule())
        assertEquals(0 to 0, result)
    }

    @Test
    fun less_than_n_days_in_week() {
        val days = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 10, 5), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 7), 1),
        ).toDays(1)
        val weeks = days.toWeeks(4)
        val result = calculateStreak(weeks, NDaysPerWeekStreakRule())
        assertEquals(0 to 0, result)
    }

    @Test
    fun exactly_n_days_in_week() {
        val days = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 10, 5), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 7), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 9), 1),
        ).toDays(1)
        val weeks = days.toWeeks(3)
        val result = calculateStreak(weeks, NDaysPerWeekStreakRule())
        assertEquals(1 to 1, result)
    }

    @Test
    fun more_than_n_days_in_week() {
        val days = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 10, 5), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 6), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 7), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 9), 1),
        ).toDays(1)
        val weeks = days.toWeeks(3)
        val result = calculateStreak(weeks, NDaysPerWeekStreakRule())
        assertEquals(1 to 1, result)
    }

    @Test
    fun two_satisfied_weeks() {
        val days = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 10, 5), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 7), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 9), 1),

            HabitLog(1, 1, LocalDate.of(2026, 10, 12), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 14), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 16), 1),
        ).toDays(1)
        val weeks = days.toWeeks(3)
        val result = calculateStreak(weeks, NDaysPerWeekStreakRule())
        assertEquals(2 to 2, result)
    }

    @Test
    fun unsatisfied_week_breaks_streak() {
        val days = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 10, 5), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 7), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 9), 1),

            HabitLog(1, 1, LocalDate.of(2026, 10, 12), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 14), 1),

            HabitLog(1, 1, LocalDate.of(2026, 10, 19), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 21), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 23), 1),
        ).toDays(1)
        val weeks = days.toWeeks(3)
        val result = calculateStreak(weeks, NDaysPerWeekStreakRule())
        assertEquals(1 to 1, result)
    }

    @Test
    fun best_streak_larger_than_current() {
        val days = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 8, 3), 1),
            HabitLog(1, 1, LocalDate.of(2026, 8, 5), 1),
            HabitLog(1, 1, LocalDate.of(2026, 8, 7), 1),

            HabitLog(1, 1, LocalDate.of(2026, 8, 10), 1),
            HabitLog(1, 1, LocalDate.of(2026, 8, 12), 1),
            HabitLog(1, 1, LocalDate.of(2026, 8, 14), 1),

            HabitLog(1, 1, LocalDate.of(2026, 8, 16), 1),
            HabitLog(1, 1, LocalDate.of(2026, 8, 17), 1),

            HabitLog(1, 1, LocalDate.of(2026, 10, 3), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 4), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 5), 1),
        ).toDays(1)
        val weeks = days.toWeeks(3)
        val result = calculateStreak(weeks, NDaysPerWeekStreakRule())
        assertEquals(1 to 2, result)
    }
}