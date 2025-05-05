package com.roswer.domain.module.onboarding.model

data class Onboarding(
    val id: Int = 0,
    val title: String,
    val description: String,
    val icon: String = "",
    val uri: String
) {
    companion object {
        fun buildFake() = Onboarding(
            id = 1,
            title = "Lorem ipsum dolor sit amet.",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            icon = "🤖",
            uri = "https://fakeimg.pl/250"
        )
    }
}