package com.roswer.financereport.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.roswer.financereport.ui.theme.FinanceReportTheme

@Composable
fun TextFontWeight(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        modifier = modifier.fillMaxHeight(),
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = fontWeight)
    )
}

@Preview(
    name = "Text Dark Screen",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Text Screen",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
fun TextFontWeightPreview() {
    FinanceReportTheme {
        TextFontWeight(text = "Text with font weight", fontWeight = FontWeight.Bold)
    }
}