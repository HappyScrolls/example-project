package com.yedongsoon.example_project.application

import com.yedongsoon.example_project.application.notification.NotificationCommandService
import com.yedongsoon.example_project.application.notification.model.FcmNotificationSendCommand
import com.yedongsoon.example_project.domain.schedule.ScheduleRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ScheduledTask(
        private val scheduleRepository: ScheduleRepository,
        private val notificationCommandService: NotificationCommandService,
) {

    @Scheduled(fixedRate = 60_000) // 1분(60,000ms)마다 실행
    fun runTask() {
        println("1분마다 실행됨: ${LocalDateTime.now()}")
        scheduleRepository.findAllByScheduleStartAt(LocalDateTime.now()).forEach {
            notificationCommandService.sendFcmNotification(
                    FcmNotificationSendCommand(
                            memberNo = it.accountNo,
                            title = it.scheduleName,
                            body = "${it.scheduleName} 일정 시간임",
                            uri = "/calendar/${it.scheduleAt}"
                    )
            )
        }
    }
}
