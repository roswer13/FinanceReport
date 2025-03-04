package com.example.financereport.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.financereport.navigation.Navigation
import com.example.financereport.presentation.onboarding.viewmodel.MutableOnboardingUiState
import com.example.financereport.presentation.onboarding.viewmodel.OnboardingUiAction
import com.example.financereport.presentation.onboarding.viewmodel.OnboardingUiEvent
import com.example.financereport.presentation.onboarding.viewmodel.OnboardingUiState
import com.example.financereport.presentation.onboarding.viewmodel.OnboardingViewModel
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

    Column {
        HorizontalPager(
            state = pagerState, modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            OnboardingItem(onboarding[page])
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            Text("Skip", style = TextStyle(
                color = Color(0xFFAAAAAA),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
            ), modifier = Modifier.clickable {
                val skipPage = pagerState.pageCount - 1
                coroutineScope.launch { pagerState.animateScrollToPage(skipPage) }
            })

            Row(
                horizontalArrangement = Arrangement.Center, modifier = Modifier.weight(1f)
            ) {
                repeat(onboarding.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .width(if (isSelected) 18.dp else 8.dp)
                            .height(if (isSelected) 8.dp else 8.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFF707784),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .background(
                                color = if (isSelected) Color(0xFF3B6C64) else Color(0xFFFFFFFF),
                                shape = CircleShape
                            )
                    )
                }
            }

            Text("Next", style = TextStyle(
                color = Color(0xFF333333),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
            ), modifier = Modifier.clickable {
                val currentPage = pagerState.currentPage
                if (currentPage < onboarding.size - 1) {
                    val nextPage = pagerState.currentPage + 1
                    coroutineScope.launch { pagerState.animateScrollToPage(nextPage) }
                }
                if (currentPage == onboarding.size - 1) {
                    viewModel.onOnboardingCompleted()
                }
            })
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