package com.example.habittracker.domain.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.test.book_ribbon
import com.example.test.directions_run
import com.example.test.laptop_mac
import com.example.test.self_improvement
import com.example.test.water_full
import java.time.LocalDate
import java.time.LocalTime

data class Habit(
    val id: Int,
    val name: String,
    val icon: HabitIcon,
    val target: Int?,
    val unit: String?,
    val startDate: LocalDate,
    val frequency: Frequency,
    val reminderTime: LocalTime? = null,
    val logs: List<HabitLog>? = null
)

enum class HabitIcon(val icon: ImageVector) {
    BOOK(book_ribbon),
    FITNESS(directions_run),
    CODING(laptop_mac),
    MEDITATION(self_improvement),
}