package com.example.habittracker

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittracker.ui.form.HabitFormScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(modifier: Modifier = Modifier) {
    HabitFormScreen(modifier.padding(32.dp))
}

@Preview
@Composable
private fun AppPreview() {
    App(
        modifier = Modifier.padding(16.dp)
    )
}