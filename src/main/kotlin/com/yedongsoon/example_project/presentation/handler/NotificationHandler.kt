package com.yedongsoon.example_project.presentation.handler

import com.yedongsoon.example_project.application.notification.NotificationCommandService
import com.yedongsoon.example_project.presentation.extension.extractMemberCodeHeader
import com.yedongsoon.example_project.presentation.handler.model.FcmKeyRefreshRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBodyOrNull
import org.springframework.web.reactive.function.server.buildAndAwait


@Service
class NotificationHandler(
        private val notificationCommandService: NotificationCommandService,
) {
    suspend fun refreshFcmKey(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val memberHeader = request.extractMemberCodeHeader()
        val command = request.awaitBodyOrNull<FcmKeyRefreshRequest>()?.toCommand(memberHeader.no)
                ?: throw IllegalArgumentException()

        notificationCommandService.refreshFcmKey(command)

        ServerResponse.noContent().buildAndAwait()
    }
}