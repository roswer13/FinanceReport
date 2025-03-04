package com.example.financereport.navigation.navhost

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.financereport.navigation.Navigation
import com.example.financereport.navigation.Route
import com.example.financereport.presentation.home.HomeScreen
import com.example.financereport.presentation.onboarding.OnboardingScreen
import com.example.financereport.presentation.splash.SplashScreen

@Composable
fun NavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = Navigation.Splash.destination) {
        composable(Navigation.Splash.destination) {
            SplashScreen(onNavigate = {
                navController.navigate(Navigation.Onboarding.destination) {
                    popUpTo(Navigation.Splash.destination) { inclusive = true }
                }
            })
        }
        mainNavigation(navController)
    }
}

private fun NavGraphBuilder.mainNavigation(navController: NavHostController) {
    navigation(startDestination = Navigation.Home.destination, route = Route.Main.destination) {
        composable(Navigation.Home.destination) {
            HomeScreen(navController = navController)
        }
        composable(Navigation.Onboarding.destination) {
            OnboardingScreen(navController = navController)
        }
    }
}
