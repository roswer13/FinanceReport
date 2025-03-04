package com.example.domain.module.onboarding.model

data class Onboarding(
    val id: Int = 0,
    val title: String,
    val description: String,
    val uri: String
) {
    companion object {
        fun buildFake() = Onboarding(
            id = 1,
            title = "Title",
            description = "Description",
            uri = "https://fakeimg.pl/250"
        )
    }
}