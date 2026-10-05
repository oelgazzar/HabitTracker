package com.example.habittracker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.getSelectedDate
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittracker.domain.models.HabitLog
import com.example.habittracker.domain.occurrence.DailyOccurrenceProvider
import com.example.habittracker.domain.occurrence.WeeklyOccurrenceProvider
import com.example.habittracker.domain.streak.DailyStreakRule
import com.example.habittracker.domain.streak.NDaysPerWeekStreakRule
import com.example.habittracker.domain.streak.SpecificDaysPerWeekStreakRule
import com.example.habittracker.domain.streak.calculateStreak
import java.time.DayOfWeek

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(modifier: Modifier = Modifier) {
    val datePickerState = rememberDatePickerState()
    val dates = remember { mutableStateListOf(datePickerState.getSelectedDate()) }
    val occurrenceProvider = remember { DailyOccurrenceProvider() }
    var streak by remember { mutableStateOf(Pair<Int, Int>(0, 0)) }

    LaunchedEffect(datePickerState.getSelectedDate()) {
        dates.add(datePickerState.getSelectedDate())

        val occurrences = occurrenceProvider.getOccurrences(
            dates.filterNotNull().map {
                HabitLog(1, 1, it, 1)
            }, 1
        )
        streak = calculateStreak(occurrences.filter { it.isCompleted },
            SpecificDaysPerWeekStreakRule(setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY, DayOfWeek.MONDAY)))
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DatePicker(datePickerState)

        FlowRow() {
            dates.distinct().filterNotNull().forEach { date ->
                InputChip(
                    selected = false,
                    onClick = { dates.remove(date) },
                    label = {
                        Text(date.toString())
                    }
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text("${streak.first} / ${streak.second}",
            style = MaterialTheme.typography.displaySmall)
        Button(onClick = { dates.clear() } ) {
            Text("Clear All")
        }


    }
}

@Preview
@Composable
private fun AppPreview() {
    App(
        modifier = Modifier.padding(16.dp)
    )
}