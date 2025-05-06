package com.roswer.financereport.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.roswer.financereport.R
import com.roswer.financereport.navigation.Navigation
import com.roswer.financereport.presentation.onboarding.components.ButtonText
import com.roswer.financereport.presentation.onboarding.components.Dot
import com.roswer.financereport.presentation.onboarding.components.OnboardingItem
import com.roswer.financereport.presentation.onboarding.viewmodel.MutableOnboardingUiState
import com.roswer.financereport.presentation.onboarding.viewmodel.OnboardingUiAction
import com.roswer.financereport.presentation.onboarding.viewmodel.OnboardingUiEvent
import com.roswer.financereport.presentation.onboarding.viewmodel.OnboardingUiState
import com.roswer.financereport.presentation.onboarding.viewmodel.OnboardingViewModel
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(), navController: NavHostController
) {
    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                OnboardingUiEvent.OnOnboardingCompleted -> {
                    navController.navigate(Navigation.Home.destination) {
                        popUpTo(Navigation.Onboarding.destination) { inclusive = true }
                    }
                }
            }
        }
    }

    OnboardingScreen(viewModel = viewModel, uiState = uiState)
}

@Composable
fun OnboardingScreen(viewModel: OnboardingUiAction, uiState: OnboardingUiState) {
    val onboarding = uiState.onboarding
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { onboarding.size })
    val coroutineScope = rememberCoroutineScope()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HorizontalPager(
                state = pagerState, modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                OnboardingItem(onboarding[page], currentPage = pagerState.currentPage, page = page)
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {

                ButtonText(
                    text = stringResource(id = R.string.skip),
                    onClick = {
                        val skipPage = pagerState.pageCount - 1
                        coroutineScope.launch { pagerState.animateScrollToPage(skipPage) }
                    }
                )

                Row(
                    horizontalArrangement = Arrangement.Center, modifier = Modifier.weight(1f)
                ) {
                    repeat(onboarding.size) { index ->
                        Dot(isSelected = pagerState.currentPage == index)
                    }
                }
                ButtonText(
                    text = stringResource(id = R.string.next),
                    onClick = {
                        val currentPage = pagerState.currentPage
                        if (currentPage < onboarding.size - 1) {
                            val nextPage = pagerState.currentPage + 1
                            coroutineScope.launch { pagerState.animateScrollToPage(nextPage) }
                        }
                        if (currentPage == onboarding.size - 1) {
                            viewModel.onOnboardingCompleted()
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(
        viewModel = OnboardingUiAction.buildFake(), uiState = MutableOnboardingUiState.buildFake()
    )
}

@Preview
@Composable
fun OnboardingScreenDarkPreview(darkTheme: Boolean = true) {
    OnboardingScreen(
        viewModel = OnboardingUiAction.buildFake(), uiState = MutableOnboardingUiState.buildFake()
    )
}