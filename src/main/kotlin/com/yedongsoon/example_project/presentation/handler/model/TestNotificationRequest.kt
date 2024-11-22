package com.yedongsoon.example_project.presentation.handler.model

data class TestNotificationRequest(
        val memberNo: Int,
        val title: String,
        val body: String,
        val uri: String,
)
