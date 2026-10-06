package com.example.habittracker.domain

import com.example.habittracker.domain.models.HabitLog
import com.example.habittracker.domain.models.toDayRecords
import com.example.habittracker.domain.streak.SpecificDaysPerWeekStreakRule
import com.example.habittracker.domain.streak.calculateStreak
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDate

class SpecificDaysPerWeekStreakRuleTest {
    @Test
    fun empty_records_returns_zero_streak() {
        val result = calculateStreak(emptyList(), SpecificDaysPerWeekStreakRule(setOf()))
        assertEquals(0 to 0, result)
    }

    @Test
    fun one_satisfied_day_returns_one() {
        val days = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 10, 5), 1)
        ).toDayRecords(1)

        val result = calculateStreak(days, SpecificDaysPerWeekStreakRule(setOf(DayOfWeek.MONDAY)))
        assertEquals(1 to 1, result)
    }

    @Test
    fun consecutive_selected_days() {
        val days = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 10, 5), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 7), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 9), 1),
        ).toDayRecords(1)

        val result = calculateStreak(days, SpecificDaysPerWeekStreakRule(setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)))
        assertEquals(3 to 3, result)
    }

    @Test
    fun non_selected_days_do_not_break_streak() {
        val days = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 10, 5), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 6), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 7), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 8), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 9), 1),
        ).toDayRecords(1)

        val result = calculateStreak(days, SpecificDaysPerWeekStreakRule(setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)))
        assertEquals(3 to 3, result)
    }

    @Test
    fun missing_selected_day_breaks_streak() {
        val days = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 10, 5), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 9), 1),
        ).toDayRecords(1)

        val result = calculateStreak(days, SpecificDaysPerWeekStreakRule(setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)))
        assertEquals(1 to 1, result)
    }

    @Test
    fun two_consecutive_weeks() {
        val days = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 10, 5), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 7), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 9), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 12), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 14), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 16), 1),
        ).toDayRecords(1)

        val result = calculateStreak(days, SpecificDaysPerWeekStreakRule(setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)))
        assertEquals(6 to 6, result)
    }

    @Test
    fun missed_day_between_weeks() {
        val days = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 10, 5), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 7), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 9), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 12), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 16), 1),
        ).toDayRecords(1)

        val result = calculateStreak(days, SpecificDaysPerWeekStreakRule(setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)))
        assertEquals(1 to 4, result)
    }

    @Test
    fun current_streak_shorter_than_best() {
        val days = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 10, 5), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 7), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 9), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 12), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 19), 1),
        ).toDayRecords(1)

        val result = calculateStreak(days, SpecificDaysPerWeekStreakRule(setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)))
        assertEquals(1 to 4, result)
    }

    @Test
    fun old_streak_followed_by_gap_and_new_streak() {
        val days = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 10, 5), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 7), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 9), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 12), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 16), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 19), 1),
        ).toDayRecords(1)

        val result = calculateStreak(days, SpecificDaysPerWeekStreakRule(setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)))
        assertEquals(2 to 4, result)
    }

    @Test
    fun records_are_unsorted() {
        val days = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 10, 9), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 5), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 7), 1),
        ).toDayRecords(1)

        val result = calculateStreak(days, SpecificDaysPerWeekStreakRule(setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)))
        assertEquals(3 to 3, result)
    }

    @Test
    fun unsatisfied_selected_day_breaks_streak() {
        val days = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 10, 5), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 9), 1),
        ).toDayRecords(1)

        val result = calculateStreak(days, SpecificDaysPerWeekStreakRule(setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)))
        assertEquals(1 to 1, result)
    }

    @Test
    fun records_from_other_week_do_not_break_selected_day_sequence() {
        val days = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 10, 9), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 12), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 14), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 16), 1),
        ).toDayRecords(1)

        val result = calculateStreak(days, SpecificDaysPerWeekStreakRule(setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)))
        assertEquals(4 to 4, result)
    }
}