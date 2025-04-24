package com.roswer.financereport.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import java.util.Calendar

@Composable
fun YearPicker(
    selectedYear: Int,
    onYearSelected: (Int) -> Unit,
    startYear: Int = 2000,
    endYear: Int = Calendar.getInstance().get(Calendar.YEAR)
) {

    val years = (startYear..endYear).toList()
    var expanded by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.clip(RoundedCornerShape(50)),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        TextButton(onClick = { expanded = true }) {
            Text(text =selectedYear.toString())
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            years.forEach { year ->
                DropdownMenuItem(text = { Text(text =year.toString()) }, onClick = {
                    onYearSelected(year)
                    expanded = false
                })
            }
        }
    }
}

@Preview
@Composable
fun YearPickerPreview() {
    YearPicker(selectedYear = 2020, onYearSelected = {})
}