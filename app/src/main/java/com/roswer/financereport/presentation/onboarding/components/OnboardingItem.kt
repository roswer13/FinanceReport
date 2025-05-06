package com.roswer.financereport.presentation.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roswer.domain.module.onboarding.model.Onboarding

@Composable
fun OnboardingItem(onboarding: Onboarding, currentPage: Int, page: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        EmojiAnimated(emoji = onboarding.icon, currentPage = currentPage, page = page)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = onboarding.title,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 36.sp),
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = onboarding.description,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingItemPreview() {
    OnboardingItem(onboarding = Onboarding.buildFake(), currentPage = 0, page = 0)
}
