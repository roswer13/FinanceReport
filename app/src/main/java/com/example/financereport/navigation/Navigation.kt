package com.example.financereport.navigation

private const val SLASH = "/"

sealed class Navigation(val destination: String) {
    object Splash : Navigation("splash")
    object Home : Navigation("home")
    object Onboarding : Navigation("onboarding")
}

sealed class Route(val value: String) {
    object Main : Navigation("main")
}