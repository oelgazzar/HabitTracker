package com.example.habittracker.ui.form

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittracker.domain.models.Frequency
import com.example.habittracker.domain.models.FrequencyType
import com.example.habittracker.domain.models.Habit
import com.example.habittracker.domain.models.HabitIcon
import com.example.habittracker.ui.theme.AppTheme
import com.example.habittracker.ui.theme.icons.check
import com.example.test.close
import com.example.test.notifications
import java.time.DayOfWeek
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitFormScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add habit")
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(check, null)
                    }
                }
            )
        }
    ) {
        HabitFormBody(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitFormBody(modifier: Modifier = Modifier) {
    var habit by remember {
        mutableStateOf(
            Habit(
                name = "Drink water",
                icon = HabitIcon.FITNESS,
                target = null,
                unit = null,
                startDate = LocalDate.now(),
                frequency = Frequency(FrequencyType.DAILY),
                reminderTime = null

            )
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {

        /* ---------------- Name ------------------ */
        Text("Name")
        OutlinedTextField(
            value = habit.name,
            onValueChange = { habit = habit.copy(name = it) },
            placeholder = {
                Text("e.g. Drink Water")
            }
        )

        /* ---------------- Icon --------------------- */
        Text("Icon")
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            HabitIcon.entries.forEach { icon ->
                FilterChip(
                    selected = icon == habit.icon,
                    onClick = { habit = habit.copy(icon = icon) },
                    label = {
                        Icon(icon.icon, null)
                    }
                )
            }
        }

        /* ----------------- Target ------------------ */
        Text("Target (optional)")
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = habit.target?.toString() ?: "",
                onValueChange = { habit = habit.copy(target = it.toIntOrNull() ?: 1) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                enabled = habit.target != null,
                modifier = Modifier
                    .weight(1f)
            )

            UnitSelector(
                selectedUnit = habit.unit ?: "ml",
                onUnitSelected = { habit = habit.copy(unit = it) },
                enabled = habit.target != null,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = habit.target == null,
                onCheckedChange = { habit = habit.copy(target = if (it) null else 1) },
                modifier = Modifier.padding(end = 4.dp)
            )
            Text("Set One-time habit")
        }

        Text("Frequency")
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            FilterChip(
                selected = habit.frequency.type == FrequencyType.DAILY,
                onClick = { habit = habit.copy(frequency = habit.frequency.copy(type = FrequencyType.DAILY)) },
                label = {
                    Text("Daily")
                }
            )
            FilterChip(
                selected = habit.frequency.type == FrequencyType.SELECTED_DAYS_PER_WEEK,
                onClick = { habit = habit.copy(frequency = habit.frequency.copy(type = FrequencyType.SELECTED_DAYS_PER_WEEK, selectedDays = emptyList())) },
                label = {
                    Text("Selected days per week")
                }
            )
            FilterChip(
                selected = habit.frequency.type == FrequencyType.EVERY_N_DAYS,
                onClick = { habit = habit.copy(frequency = Frequency(type = FrequencyType.EVERY_N_DAYS, dayInterval = 1)) },
                label = {
                    Text("Every X days")
                }
            )
            FilterChip(
                selected = habit.frequency.type == FrequencyType.N_DAYS_PER_WEEK,
                onClick = { habit = habit.copy(frequency = Frequency(type = FrequencyType.N_DAYS_PER_WEEK, nDaysPerWeek = 3)) },
                label = {
                    Text("X times per week")
                }
            )
        }

        AnimatedVisibility(habit.frequency.type == FrequencyType.SELECTED_DAYS_PER_WEEK) {
            Column {
                Text("Select days:")
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listOf(
                        DayOfWeek.SATURDAY,
                        DayOfWeek.SUNDAY,
                        DayOfWeek.MONDAY,
                        DayOfWeek.TUESDAY,
                        DayOfWeek.WEDNESDAY,
                        DayOfWeek.THURSDAY,
                        DayOfWeek.FRIDAY,
                    ).forEach { dayOfWeek ->
                        FilterChip(
                            selected = habit.frequency.selectedDays?.contains(dayOfWeek) == true ,
                            onClick = {
                                habit = habit.copy(
                                    frequency = habit.frequency.copy(
                                        selectedDays = habit.frequency.selectedDays?.plus(dayOfWeek)?.distinct()
                                    )
                                )
                            },
                            label = {
                                Text(
                                    dayOfWeek.name.lowercase()
                                        .replaceFirstChar { it.uppercase() })
                            }
                        )
                    }
                }
            }
        }

        AnimatedVisibility(habit.frequency.type == FrequencyType.EVERY_N_DAYS) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Repeat Every")
                BasicTextField(
                    value = habit.frequency.dayInterval.toString(),
                    onValueChange = {
                        habit = habit.copy(
                            frequency = habit.frequency.copy(
                                dayInterval = it.toIntOrNull()
                            )
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .border(
                            1.dp, MaterialTheme.colorScheme.outline,
                            RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Text("days")
            }
        }

        AnimatedVisibility(habit.frequency.type == FrequencyType.N_DAYS_PER_WEEK) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Do")
                BasicTextField(
                    value = habit.frequency.nDaysPerWeek.toString(),
                    onValueChange = {
                        habit = habit.copy(
                            frequency = habit.frequency.copy(
                                nDaysPerWeek = it.toIntOrNull()
                            )
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .border(
                            1.dp, MaterialTheme.colorScheme.outline,
                            RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .padding(ImePadding)
                )
                Text("per week")
            }
        }


        Text("Reminder (optional)")
        habit.reminderTime?.let {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                    .padding(4.dp)
            ) {
                Icon(
                    notifications,
                    null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    "8:00 AM",
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = {}) {
                    Icon(close, null)
                }
            }
        }?: TextButton(
            onClick = {}
        ) {
            Text("Add a reminder")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row() {
            Text(
                "Show on screen",
                modifier = Modifier.weight(1f)
            )

            Switch(
                checked = true,
                onCheckedChange = {}
            )

        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HabitFormBodyPrev() {
    AppTheme {
        HabitFormBody(modifier = Modifier.padding(16.dp))
    }
}