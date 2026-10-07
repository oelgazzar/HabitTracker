package com.example.habittracker.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittracker.domain.models.DayRecord
import com.example.habittracker.ui.components.HabitItem
import com.example.habittracker.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    todayRecords: List<DayRecord>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Today",
                        modifier = Modifier.width(100.dp)
                    )
                }
            )
        }
    ) {
        HomeBody(
            todayRecords = todayRecords,
            modifier = modifier
            .fillMaxWidth()
            .padding(it)
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun HomeBody(
    todayRecords: List<DayRecord>,
    modifier: Modifier = Modifier
) {
    var filterOption by remember { mutableStateOf(TodayHabitsFilterOptions.ALL) }

    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        TodayProgressIndicator(
            progress = todayRecords.sumOf { if(it.isSatisfied) 1 else 0 },
            target = todayRecords.size,
            modifier = Modifier.fillMaxWidth()
        )
        TodayHabitsFilterChips(
            allCount = todayRecords.size,
            doneCount = todayRecords.sumOf { if(it.isSatisfied) 1 else 0 },
            filterOption = filterOption,
            setFilterOption = { filterOption = it },
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            todayRecords
                .filter {
                    when(filterOption) {
                        TodayHabitsFilterOptions.ALL -> true
                        TodayHabitsFilterOptions.DONE -> it.isSatisfied
                        TodayHabitsFilterOptions.REMAINING -> !it.isSatisfied
                    }
                }
                .forEachIndexed { index, record ->
                HabitItem(
                    record
                )

                if (index < todayRecords.lastIndex) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outline.copy(alpha = .1f),
                    )
                }

            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeBodyPrev() {
    AppTheme {
        HomeBody(
            todayRecords = emptyList(),
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
        )
    }
}