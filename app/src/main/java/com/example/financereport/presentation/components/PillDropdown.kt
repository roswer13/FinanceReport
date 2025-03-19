package com.example.financereport.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PillDropdown(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    icon: Int? = null,
    backgroundColor: String? = null
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        Surface(modifier = Modifier
            .menuAnchor()
            .clip(RoundedCornerShape(50))
            .background(backgroundColor?.toColorInt()?.let { Color(it) }
                ?: MaterialTheme.colorScheme.surfaceVariant)
            .clickable { expanded = true }
            .padding(horizontal = 8.dp),
            color = backgroundColor?.toColorInt()?.let { Color(it) }
                ?: MaterialTheme.colorScheme.surfaceVariant) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (icon != null) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                            .size(25.dp),
                        tint = Color.DarkGray
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .size(height = 25.dp, width = 1.dp),
                    )
                }
                // Validate of background color.
                if (backgroundColor == null) {
                    Text(text = selectedItem)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                } else {
                    Text(text = selectedItem, color = Color.DarkGray)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.DarkGray
                    )
                }
            }
        }

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach { item ->
                DropdownMenuItem(text = { Text(item) }, onClick = {
                    onItemSelected(item)
                    expanded = false
                })
            }
        }
    }
}

@Preview(
    locale = "fr-rFR", uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Pill Dropdown dark"
)
@Preview(
    locale = "en-rEN", uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Pill Dropdown light"
)
@Composable
fun PillDropdownPreview() {
    PillDropdown(
        items = listOf("Item 1", "Item 2", "Item 3"),
        selectedItem = "Item 1",
        onItemSelected = {},
        icon = com.example.domain.R.drawable.icon_test
    )
}

@Preview
@Composable
fun PillDropdownWithoutIconPreview() {
    PillDropdown(
        items = listOf("Item 1", "Item 2", "Item 3"),
        selectedItem = "Item 1",
        onItemSelected = {},
    )
}
