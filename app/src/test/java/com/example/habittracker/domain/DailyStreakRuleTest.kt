package com.example.habittracker.domain

import com.example.habittracker.domain.models.HabitLog
import com.example.habittracker.domain.models.toDays
import com.example.habittracker.domain.streak.DailyStreakRule
import com.example.habittracker.domain.streak.calculateStreak
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class DailyStreakRuleTest {
    // ---------------------------------------------------------
    // Empty
    // ---------------------------------------------------------

    @Test
    fun `empty records returns zero current and zero best`() {
        val result = calculateStreak(
            records = emptyList(),
            rule = DailyStreakRule()
        )

        assertEquals(0 to 0, result)
    }

    // ---------------------------------------------------------
    // Single record
    // ---------------------------------------------------------

    @Test
    fun `one satisfied record returns one current and one best`() {
        val today = LocalDate.now()

        val result = calculateStreak(
            records = listOf(
                HabitLog(1, 1, today, 1)
            ).toDays(1),
            rule = DailyStreakRule()
        )

        assertEquals(1 to 1, result)
    }

    // ---------------------------------------------------------
    // Consecutive records
    // ---------------------------------------------------------

    @Test
    fun `three consecutive satisfied records return three current and three best`() {
        val today = LocalDate.now()

        val records = listOf(
            HabitLog(1,1,today.minusDays(2), 1),
            HabitLog(1,1,today.minusDays(1), 1),
            HabitLog(1,1,today,1)
        ).toDays(1)

        val result = calculateStreak(
            records = records,
            rule = DailyStreakRule()
        )

        assertEquals(3 to 3, result)
    }

    @Test
    fun `five consecutive records return five current and five best`() {
        val today = LocalDate.now()

        val records = (4L downTo 0L).map {
            HabitLog(1, 1, today.minusDays(it), 1)
        }.toDays(1)

        val result = calculateStreak(
            records = records,
            rule = DailyStreakRule()
        )

        assertEquals(5 to 5, result)
    }

    // ---------------------------------------------------------
    // Gap
    // ---------------------------------------------------------

    @Test
    fun `gap splits streak and current streak starts again`() {
        val today = LocalDate.now()

        val records = listOf(
            HabitLog(1, 1, today.minusDays(4), 1),
            HabitLog(1,1, today.minusDays(3),1),
            // gap
            HabitLog(1,1,today.minusDays(1),1),
            HabitLog(1,1,today,1)
        ).toDays(1)

        val result = calculateStreak(
            records = records,
            rule = DailyStreakRule()
        )

        assertEquals(2 to 2, result)
    }

    @Test
    fun `current streak can be shorter than best streak`() {
        val today = LocalDate.now()

        val records = listOf(
            // Old streak of 4
            HabitLog(1,1,today.minusDays(6),1),
            HabitLog(1,1,today.minusDays(5), 1),
            HabitLog(1,1,today.minusDays(4), 1),
            HabitLog(1,1,today.minusDays(3), 1),

            // Gap

            // Current streak of 2
            HabitLog(1,1,today.minusDays(1), 1),
            HabitLog(1,1,today, 1)
        ).toDays(1)

        val result = calculateStreak(
            records = records,
            rule = DailyStreakRule()
        )

        assertEquals(2 to 4, result)
    }

    // ---------------------------------------------------------
    // Last record is not today
    // ---------------------------------------------------------

    @Test
    fun `streak becomes zero when last completed record is not active`() {
        val today = LocalDate.now()

        val records = listOf(
            HabitLog(1,1,today.minusDays(3),1),
            HabitLog(1,1,today.minusDays(2),1),
            HabitLog(1,1,today.minusDays(1),1)
        ).toDays(1)

        val result = calculateStreak(
            records = records,
            rule = DailyStreakRule()
        )

        assertEquals(3 to 3, result)
    }

    @Test
    fun `old best streak is preserved when current streak is inactive`() {
        val today = LocalDate.now()

        val records = listOf(
            // Best = 4
            HabitLog(1,1,today.minusDays(10),1),
            HabitLog(1,1,today.minusDays(9),1),
            HabitLog(1,1,today.minusDays(8),1),
            HabitLog(1,1,today.minusDays(7),1),

            // Later streak = 2, but inactive
            HabitLog(1,1,today.minusDays(2),1),
            HabitLog(1,1,today.minusDays(1),1)
        ).toDays(1)

        val result = calculateStreak(
            records = records,
            rule = DailyStreakRule()
        )

        assertEquals(2 to 4, result)
    }

    // ---------------------------------------------------------
    // Unsatisfied records
    // ---------------------------------------------------------

    @Test
    fun `unsatisfied records are ignored`() {
        val today = LocalDate.of(2026, 10, 6) // Monday

        val records = listOf(
            HabitLog(1,1,today.minusDays(2), 1),

            // Not satisfied
            HabitLog(1,1,today.minusDays(1), 0),

            HabitLog(1,1,today, 1)
        ).toDays(1)

        val result = calculateStreak(
            records = records,
            rule = DailyStreakRule()
        )

        println(result)

        assertEquals(1 to 1, result)
    }
}