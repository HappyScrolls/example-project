package com.yedongsoon.example_project.application.notification.model

data class FcmNotificationSendCommand(
        val memberNo: Int,
        val title: String,
        val body: String,
        val uri: String,
)
