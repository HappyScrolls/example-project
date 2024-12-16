package com.yedongsoon.example_project.domain.notification

import com.yedongsoon.example_project.enums.NotificationHistoryType

data class NotificationCreateCommand(
        val historyType: NotificationHistoryType,
        val accountNo: Int,
        val message: String,
        val path: String? = null,
        var parameter: Any? = null,
)
