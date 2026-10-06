package com.example.habittracker.domain

import com.example.habittracker.domain.models.HabitLog
import com.example.habittracker.domain.models.toDays
import com.example.habittracker.domain.models.toWeeks
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.LocalDate

class RecordTest {
    @Test
    fun toDays_groupsLogsCorrectly() {
        val logs = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 10, 1), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 1), 1),
            HabitLog(1, 1, LocalDate.of(2026, 10, 3), 1),
        )

        val days = logs.toDays(1)
        println(days)

        assertEquals(2, days.size)
        assertEquals(1, days.first().target)
        assertEquals(2, days.first().logs.size)
    }

    @Test
    fun toWeeks_groupsDaysCorrectly() {
        val logs = listOf(
            HabitLog(1, 1, LocalDate.of(2026, 10, 3), 1), // Saturday
            HabitLog(1, 1, LocalDate.of(2026, 10, 5), 1), // Monday
            HabitLog(1, 1, LocalDate.of(2026, 10, 11), 1), // Sunday of following week
        )

        val days = logs.toDays(1)
        val weeks = days.toWeeks(3)
        println(weeks)

        assertEquals(2, weeks.size)
        assertEquals(2, weeks.first().days.size)
    }
}