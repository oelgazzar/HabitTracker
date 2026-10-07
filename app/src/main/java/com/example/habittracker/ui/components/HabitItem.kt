package com.example.habittracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.habittracker.domain.models.DayRecord
import com.example.habittracker.domain.models.Frequency
import com.example.habittracker.domain.models.FrequencyType
import com.example.habittracker.domain.models.Habit
import com.example.habittracker.domain.models.HabitIcon
import com.example.habittracker.domain.models.HabitLog
import com.example.habittracker.ui.theme.AppTheme
import com.example.test.water_full
import java.time.LocalDate

@Composable
fun HabitItem(
    dayRecord: DayRecord,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            dayRecord.habit.icon.icon,
            null,
            tint = Color.White,
            modifier = Modifier
                .padding(end = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(dayRecord.habit.icon.color)
                .padding(8.dp)
                .size(36.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(dayRecord.habit.name)
            Text(
                "${dayRecord.progress} / ${dayRecord.target} ${dayRecord.habit.unit}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Normal
            )
            LinearProgressIndicator(
                progress = { dayRecord.progress / dayRecord.target.toFloat() },
            )
        }

        Text("%.0f%%".format(dayRecord.progress / dayRecord.target.toFloat() * 100))
    }
}

@Preview
@Composable
private fun HabitItemPrev() {
    AppTheme {
        HabitItem(
            DayRecord(
                habit = Habit(
                    id = 0,
                    name = "Running",
                    icon = HabitIcon.FITNESS,
                    target = 8,
                    unit = "Km",
                    startDate = LocalDate.now(),
                    frequency = Frequency(
                        type = FrequencyType.DAILY,
                        dayInterval = 1
                    )
                ),
                anchorDate = LocalDate.now(),
                logs = listOf(HabitLog(
                    id = 1,
                    habitId = 1,
                    date = LocalDate.now(),
                    value = 5,
                ))
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}