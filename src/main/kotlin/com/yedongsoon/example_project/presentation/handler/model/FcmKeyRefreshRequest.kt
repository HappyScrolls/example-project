package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.application.notification.model.FcmKeyRefreshCommand

data class FcmKeyRefreshRequest(
        val key: String,
) {

    fun toCommand(memberNo: Int) = FcmKeyRefreshCommand(
            memberNo = memberNo,
            key = key
    )

}