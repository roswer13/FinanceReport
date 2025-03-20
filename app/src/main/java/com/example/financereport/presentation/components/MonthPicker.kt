package com.example.financereport.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financereport.R
import com.example.financereport.ui.theme.FinanceReportTheme

@Composable
fun MonthPicker(selectedMonth: Int, onMonthSelected: (Int) -> Unit) {
    val months = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)

    if (selectedMonth !in months) {
        return Box {}
    }

    var expanded by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.clip(RoundedCornerShape(50)),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        TextButton( onClick = { expanded = true }) {
            Text(text = getMonthName(month = selectedMonth))
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            months.forEach { month ->
                DropdownMenuItem(text = { Text(getMonthName(month)) }, onClick = {
                    onMonthSelected(month)
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun getMonthName(month: Int): String {
    return when (month) {
        0 -> stringResource(R.string.january)
        1 -> stringResource(R.string.february)
        2 -> stringResource(R.string.march)
        3 -> stringResource(R.string.april)
        4 -> stringResource(R.string.may)
        5 -> stringResource(R.string.june)
        6 -> stringResource(R.string.july)
        7 -> stringResource(R.string.august)
        8 -> stringResource(R.string.september)
        9 -> stringResource(R.string.october)
        10 -> stringResource(R.string.november)
        11 -> stringResource(R.string.december)
        else -> "Invalid month"
    }
}

@Preview(
    locale = "fr-rFR",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Month dark Picker"
)
@Preview(
    locale = "en-rEN",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Month light Picker"
)
@Composable
fun MonthPickerPreview() {
    FinanceReportTheme {
        MonthPicker(selectedMonth = 3) {}
    }
}

@Preview(showBackground = true, name = "Bad Month Picker")
@Composable
fun MonthPickerBadPreview() {
    MonthPicker(selectedMonth = 15) {}
}