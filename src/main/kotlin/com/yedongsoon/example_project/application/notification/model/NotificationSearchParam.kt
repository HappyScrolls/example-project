package com.yedongsoon.example_project.application.notification.model

import org.springframework.data.domain.PageRequest

data class NotificationSearchParam(
        val memberNo: Int,
        val pageRequest: PageRequest,
)
