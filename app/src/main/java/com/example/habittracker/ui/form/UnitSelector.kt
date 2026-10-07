package com.example.habittracker.ui.form

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittracker.ui.theme.AppTheme
import com.example.habittracker.ui.theme.icons.check

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitSelector(
    selectedUnit: String,
    onUnitSelected: (String) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val units = listOf("ml", "minutes")

    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = { if(enabled) expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedUnit,
            onValueChange = {},
            readOnly = true,
            enabled = enabled,
            singleLine = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = MenuDefaults.containerColor,
            shape = MenuDefaults.shape,
        ) {
            units.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        onUnitSelected(unit)
                        expanded = false
                    },
                    leadingIcon = {
                        if (selectedUnit == unit) {
                            Icon(
                                check,
                                contentDescription = null,
                            )
                        }
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UnitSelectorPrev() {
    AppTheme {
        UnitSelector(
            selectedUnit = "ml",
            onUnitSelected = {},
            enabled = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}