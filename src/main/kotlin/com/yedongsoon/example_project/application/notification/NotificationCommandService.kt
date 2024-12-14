package com.yedongsoon.example_project.application.notification

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import com.yedongsoon.example_project.application.exception.MemberNotFoundException
import com.yedongsoon.example_project.application.notification.model.FcmKeyRefreshCommand
import com.yedongsoon.example_project.application.notification.model.FcmNotificationSendCommand
import com.yedongsoon.example_project.application.notification.model.NotificationSearchParam
import com.yedongsoon.example_project.domain.member.MemberRepository
import com.yedongsoon.example_project.domain.notification.NotificationCreateCommand
import com.yedongsoon.example_project.domain.notification.NotificationHistory
import com.yedongsoon.example_project.domain.notification.NotificationHistoryRepository
import com.yedongsoon.example_project.presentation.handler.model.TestNotificationRequest
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class NotificationCommandService(
        private val memberRepository: MemberRepository,
        private val notificationHistoryRepository: NotificationHistoryRepository,
        private val firebaseMessaging: FirebaseMessaging,
) {
    fun refreshFcmKey(command: FcmKeyRefreshCommand) {
        memberRepository.findByNo(command.memberNo)?.let {
            it.applyFcmKey(command.key)
            memberRepository.save(it)
        } ?: throw MemberNotFoundException("회원 정보가 없습니다.")
    }

    suspend fun sendFcmNotification(command: FcmNotificationSendCommand) {
        val fcmKey = memberRepository.findByNo(command.memberNo)?.fcmKey
        if (fcmKey != null) {
            val notification = Notification.builder()
                    .setTitle(command.title)
                    .setBody(command.body)
                    .build()
            val message = Message.builder()
                    .setToken(fcmKey)
                    .setNotification(notification)
                    .putData("uri", command.uri)
                    .build()

            val response = firebaseMessaging.send(message)
            println("Successfully sent message: $response")
        }
    }

    suspend fun readNotification(notificationNo: Int) {
        notificationHistoryRepository.findByNotificationNoAndIsDeletedFalse(notificationNo)?.let {
            it.read()
            notificationHistoryRepository.save(it)
        }
    }

    fun testNotification(request: TestNotificationRequest): String {
        val fcmKey = memberRepository.findByNo(request.memberNo)?.fcmKey
        if (fcmKey != null) {
            val notification = Notification.builder()
                    .setTitle(request.title)
                    .setBody(request.body)
                    .build()
            val message = Message.builder()
                    .setToken(fcmKey)
                    .setNotification(notification)
                    .putData("uri", request.uri)
                    .build()

            val response = firebaseMessaging.send(message)
            return response
        }
        return ""
    }

    suspend fun createNotification(command: NotificationCreateCommand) {
        notificationHistoryRepository.save(NotificationHistory.create(command))
    }

    fun getNotifications(param: NotificationSearchParam): Page<NotificationHistory> {
        return notificationHistoryRepository.findByAccountNoOrderByMessagedAtDesc(param.memberNo, param.pageRequest)
    }
}
