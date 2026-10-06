package com.example.habittracker

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.habittracker.data.local.AppDatabase
import com.example.habittracker.data.local.HabitDao
import com.example.habittracker.data.repository.LocalHabitRepository
import com.example.habittracker.domain.HabitManager
import com.example.habittracker.ui.theme.HabitTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var manager: HabitManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitTrackerTheme {
                val habits by manager.getAllHabits().collectAsStateWithLifecycle(emptyList())
                val todayRecords by manager.getTodayHabitRecords().collectAsStateWithLifecycle(emptyList())
                var show by remember { mutableStateOf("all") }

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(32.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        FilterChip(show == "all", onClick = { show = "all"},
                            label = {
                                Text("All")
                            }
                        )
                        FilterChip(show == "today", onClick = { show = "today" },
                        label = {
                            Text("Today")
                        })

                    }

                    when(show) {
                        "all" -> {
                            habits.forEach {
                                Text(
                                    buildAnnotatedString {
                                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append(it.name)
                                        }
                                        append(" - ")
                                        withStyle(SpanStyle(color = androidx.compose.ui.graphics.Color.Blue)) {
                                            append(it.startDate.toString())
                                        }
                                        append(" - ${it.frequency.type}")
                                    }
                                )
                            }
                        }
                        "today" -> {
                            todayRecords.forEach {
                                Text(
                                    buildAnnotatedString {
                                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append(it.habit.name)
                                        }
                                        append(" - ")
                                        withStyle(SpanStyle(color = androidx.compose.ui.graphics.Color.Blue)) {
                                            append(it.anchorDate.toString())
                                        }
                                        append(" - ${it.habit.frequency.type}")
                                    }
                                )
                            }
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }
}