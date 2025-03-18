package com.example.financereport.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financereport.ui.theme.FinanceReportTheme


@Composable
fun KeyboardKey(
    label: String, onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceDim)
            .clickable { onClick() }, contentAlignment = Alignment.Center
    ) {
        Text(text = label, fontSize = 24.sp)
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "DefaultPreviewLight"
)
@Composable
fun KeyboardKeyPreview() {
    FinanceReportTheme(dynamicColor=false) {
        KeyboardKey(label = "1", onClick = {})
    }
}
@Preview
@Composable
fun KeyboardKeyDefaultPreview() {
    KeyboardKey(label = "1", onClick = {})
}