package com.yedongsoon.example_project.presentation.handler

import com.yedongsoon.example_project.application.notification.NotificationCommandService
import com.yedongsoon.example_project.presentation.extension.extractMemberCodeHeader
import com.yedongsoon.example_project.presentation.extension.intPathVariable
import com.yedongsoon.example_project.presentation.extension.intQueryParam
import com.yedongsoon.example_project.presentation.handler.model.FcmKeyRefreshRequest
import com.yedongsoon.example_project.presentation.handler.model.NotificationResponse
import com.yedongsoon.example_project.presentation.handler.model.NotificationSearchRequest
import com.yedongsoon.example_project.presentation.handler.model.TestNotificationRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.*


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

    suspend fun testNotification(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val request = request.awaitBodyOrNull<TestNotificationRequest>()
                ?: throw IllegalArgumentException()

        val result = notificationCommandService.testNotification(request)

        ServerResponse.ok().bodyValueAndAwait(result)
    }

    suspend fun readNotification(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val memberHeader = request.extractMemberCodeHeader()
        val notificationNo = request.intPathVariable("notificationNo")
        notificationCommandService.readNotification(notificationNo)
        ServerResponse.noContent().buildAndAwait()
    }

    suspend fun getNotifications(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val memberHeader = request.extractMemberCodeHeader()
        val page = request.intQueryParam("page")
        val size = request.intQueryParam("size")
        val param = NotificationSearchRequest(page, size).toParam(memberHeader.no)
        notificationCommandService.getNotifications(param).let { notification ->
            ServerResponse.ok().bodyValueAndAwait(notification.map { NotificationResponse.of(it) })
        }
    }

}
