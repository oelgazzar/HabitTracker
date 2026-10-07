package com.example.habittracker.ui.home

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittracker.ui.theme.AppTheme

@Composable
fun TodayProgressIndicator(
    progress: Int,
    target: Int,
    modifier: Modifier = Modifier
) {
    val percentage = "%.0f".format(progress.toFloat() / target.toFloat() * 100)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                progress = { progress.toFloat() / target },
                strokeWidth = 8.dp,
                modifier = Modifier.size(100.dp)
            )
            Text(
                "$progress/$target",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Text(
            "$percentage% Done",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview
@Composable
private fun TodayProgressIndicatorPrev() {
    AppTheme {
        TodayProgressIndicator(
            progress = 3,
            target = 5,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}