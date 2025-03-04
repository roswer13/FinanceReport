package com.example.financereport.presentation.onboarding

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.financereport.presentation.onboarding.viewmodel.OnboardingViewModel

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    navController: NavHostController
) {
    LaunchedEffect(Unit) {
        val isCompleted = viewModel.isOnboardingCompleted.value
        Log.i("OnboardingScreen", "isCompleted: $isCompleted")
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Onboarding Screen")
    }
}