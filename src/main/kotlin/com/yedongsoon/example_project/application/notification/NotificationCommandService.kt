package com.yedongsoon.example_project.application.notification

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import com.yedongsoon.example_project.application.exception.MemberNotFoundException
import com.yedongsoon.example_project.application.notification.model.FcmKeyRefreshCommand
import com.yedongsoon.example_project.application.notification.model.FcmNotificationSendCommand
import com.yedongsoon.example_project.domain.member.MemberRepository
import org.springframework.stereotype.Service

@Service
class NotificationCommandService(
        private val memberRepository: MemberRepository,
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
}