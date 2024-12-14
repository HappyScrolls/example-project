package com.yedongsoon.example_project.domain.notification

data class NotificationCreateCommand(
        val accountNo: Int,
        val message: String,
        val path: String? = null,
        var parameter: Any? = null,
)
