package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.application.notification.model.NotificationSearchParam
import org.springframework.data.domain.PageRequest

data class NotificationSearchRequest(
        val page: Int,
        val size: Int,
) {
    fun toParam(memberNo: Int) = NotificationSearchParam(
            memberNo = memberNo,
            pageRequest = PageRequest.of(page, size),
    )
}
