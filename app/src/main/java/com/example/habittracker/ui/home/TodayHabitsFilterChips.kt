package com.example.habittracker.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittracker.ui.theme.AppTheme

@Composable
fun TodayHabitsFilterChips(
    allCount: Int,
    doneCount: Int,
    filterOption: TodayHabitsFilterOptions = TodayHabitsFilterOptions.ALL,
    setFilterOption: (TodayHabitsFilterOptions) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
        modifier = modifier
    ) {
        FilterChip(
            selected = filterOption == TodayHabitsFilterOptions.ALL,
            onClick = { setFilterOption(TodayHabitsFilterOptions.ALL) },
            label = {
                Text(
                    "All ($allCount)",
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            },
        )
        FilterChip(
            selected = filterOption == TodayHabitsFilterOptions.DONE,
            onClick = { setFilterOption(TodayHabitsFilterOptions.DONE) },
            label = {
                Text(
                    "Done ($doneCount)",
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            },
        )
        FilterChip(
            selected = filterOption == TodayHabitsFilterOptions.REMAINING,
            onClick = { setFilterOption(TodayHabitsFilterOptions.REMAINING) },
            label = {
                Text(
                    "Remaining (${allCount - doneCount})",
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            },
        )
    }
}

@Preview
@Composable
private fun TodayHabitsFilterChipsPrev() {
    AppTheme {
        TodayHabitsFilterChips(
            filterOption = TodayHabitsFilterOptions.ALL,
            setFilterOption = {},
            allCount = 10,
            doneCount = 5,
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
        )
    }
}