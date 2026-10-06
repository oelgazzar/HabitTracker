package com.example.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.habittracker.domain.HabitManager
import com.example.habittracker.ui.theme.HabitTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var manager: HabitManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val todayRecordsFlow = manager.getTodayHabitRecords()
        setContent {
            HabitTrackerTheme {
                val todayRecords by todayRecordsFlow.collectAsStateWithLifecycle(emptyList())
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(32.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            progress = { todayRecords.sumOf { if (it.isSatisfied) 1 else 0 } + 5 / todayRecords.size.toFloat() },
                            strokeWidth = 8.dp  ,
                            modifier = Modifier.size(110.dp)
                        )
                        Text(
                            "${todayRecords.sumOf { if (it.isSatisfied) 1 else 0 } + 5}  / ${todayRecords.size}",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    todayRecords.forEach {
                        Card {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            ) {
                                Column {
                                    Text(it.habit.name)
                                    Text("${it.progress} / ${it.target} ${it.habit.unit}")
                                    Text(it.habit.frequency.type.name)
                                }
                                Text(if (it.isSatisfied) "Completed" else "Not completed")
                            }
                        }
                    }
                }
            }
        }
    }
}