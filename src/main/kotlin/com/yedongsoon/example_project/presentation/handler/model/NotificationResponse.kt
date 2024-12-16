package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.domain.notification.NotificationHistory
import com.yedongsoon.example_project.enums.NotificationHistoryType
import java.time.LocalDateTime

data class NotificationResponse(
        val notificationNo: Int,
        val type: NotificationHistoryType,
        val message: String,
        val path: String?,
        val parameter: Any?,
        val isRead: Boolean,
        val messagedAt: LocalDateTime,
) {
    companion object {
        fun of(notificationHistory: NotificationHistory) = NotificationResponse(
                notificationNo = notificationHistory.notificationNo,
                type = notificationHistory.historyType,
                message = notificationHistory.message,
                path = notificationHistory.path,
                parameter = notificationHistory.parameter,
                isRead = notificationHistory.isDeleted,
                messagedAt = notificationHistory.messagedAt,
        )
    }
}
