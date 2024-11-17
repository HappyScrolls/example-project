package com.yedongsoon.example_project.application.notification.model


data class FcmKeyRefreshCommand(
        val memberNo: Int,
        val key: String,
)