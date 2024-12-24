package com.yedongsoon.example_project.application.schedule

import com.yedongsoon.example_project.application.couple.CoupleQueryService
import com.yedongsoon.example_project.application.exception.ScheduleDuplicatedException
import com.yedongsoon.example_project.application.exception.ScheduleModifyRequestNotFoundException
import com.yedongsoon.example_project.application.exception.ScheduleNotFoundException
import com.yedongsoon.example_project.application.notification.NotificationCommandService
import com.yedongsoon.example_project.application.notification.model.FcmNotificationSendCommand
import com.yedongsoon.example_project.domain.notification.NotificationCreateCommand
import com.yedongsoon.example_project.domain.schedule.Schedule
import com.yedongsoon.example_project.domain.schedule.ScheduleModifyRequest
import com.yedongsoon.example_project.domain.schedule.ScheduleModifyRequestRepository
import com.yedongsoon.example_project.domain.schedule.ScheduleRepository
import com.yedongsoon.example_project.domain.schedule.model.ScheduleCreateCommand
import com.yedongsoon.example_project.domain.schedule.model.ScheduleModifyCommand
import com.yedongsoon.example_project.domain.schedule.model.ScheduleModifyRequestCreateCommand
import com.yedongsoon.example_project.enums.NotificationHistoryType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ScheduleCommandService(
        private val scheduleRepository: ScheduleRepository,
        private val coupleQueryService: CoupleQueryService,
        private val scheduleModifyRequestRepository: ScheduleModifyRequestRepository,
        private val notificationCommandService: NotificationCommandService,
) {
    // 일정 등록
    suspend fun createSchedule(command: ScheduleCreateCommand) {
        val partnerNo = coupleQueryService.getLover(command.accountNo).no
        if (scheduleRepository.existsByDuplicateSchedule(command.scheduleStartAt, command.scheduleEndAt, command.accountNo, partnerNo)) {
            throw ScheduleDuplicatedException("시간대에 겹치는 일정이 존재합니다")
        }
        val schedule = scheduleRepository.save(Schedule.create(command))
        notificationCommandService.sendFcmNotification(FcmNotificationSendCommand(
                memberNo = partnerNo,
                title = "일정 등록",
                body = "상대방이 일정을 등록했습니다",
                uri = "/calendar/${schedule.scheduleAt}"
        ))
        notificationCommandService.createNotification(NotificationCreateCommand(
                historyType = NotificationHistoryType.SCHEDULE_CREATE,
                accountNo = partnerNo,
                message = "상대방이 일정을 등록했습니다",
                path = "/calendar/${schedule.scheduleAt}"
        ))
    }

    // 일정 삭제
    fun deleteSchedule(scheduleNo: Int) {
        scheduleRepository.deleteById(scheduleNo)
    }

    fun changeStatus(scheduleNo: Int, status: String) {
        scheduleRepository.findByIdOrNull(scheduleNo)?.let {
            it.changeStatus(status)
            scheduleRepository.save(it)
        } ?: throw ScheduleNotFoundException("존재하지 않습니다")
    }

    fun setToCommonSchedule(scheduleNo: Int) {
        scheduleRepository.findByIdOrNull(scheduleNo)?.let {
            it.toCommon()
            scheduleRepository.save(it)
        } ?: throw ScheduleNotFoundException("존재하지 않습니다")
    }

    suspend fun createScheduleModifyRequest(command: ScheduleModifyRequestCreateCommand) {
        val accountNo = scheduleRepository.findByIdOrNull(command.scheduleNo)?.accountNo
                ?: throw ScheduleNotFoundException("스케줄을 찾을 수 없습니다.")
        scheduleModifyRequestRepository.findByIdOrNull(command.scheduleNo)?.let { scheduleModifyRequestRepository.delete(it) }
        val requestNo = scheduleModifyRequestRepository.save(ScheduleModifyRequest.create(command, accountNo)).no

        notificationCommandService.sendFcmNotification(FcmNotificationSendCommand(
                memberNo = accountNo,
                title = "일정 수정 요청",
                body = "일정 수정을 요청했습니다",
                uri = "/modify-request/${requestNo}"
        ))
        notificationCommandService.createNotification(NotificationCreateCommand(
                historyType = NotificationHistoryType.SCHEDULE_MODIFY_REQUEST,
                accountNo = accountNo,
                message = "일정 수정을 요청했습니다",
                path = "/modify-request/${requestNo}"
        ))
    }

    suspend fun getScheduleModifyRequest(no: Int): ScheduleModifyRequest {
        return scheduleModifyRequestRepository.findByNoAndIsAcceptedIsNotNull(no)
                ?: throw ScheduleModifyRequestNotFoundException("수정 요청이 존재하지 않습니다")
    }

    suspend fun modifySchedule(command: ScheduleModifyCommand) {
        scheduleRepository.findByIdOrNull(command.scheduleNo)?.let {
            val partnerNo = coupleQueryService.getLover(command.accountNo).no
            if (scheduleRepository.existsByDuplicateScheduleForModify(command.scheduleStartAt, command.scheduleEndAt, command.accountNo, partnerNo, command.scheduleNo)) {
                throw ScheduleDuplicatedException("시간대에 겹치는 일정이 존재합니다")
            }
            it.updateSchedule(command)
            scheduleRepository.save(it)
        } ?: throw ScheduleNotFoundException("존재하지 않습니다")
    }

    suspend fun acceptScheduleModifyRequest(no: Int) {
        scheduleModifyRequestRepository.findByIdOrNull(no)?.also {
            it.accept()
            scheduleModifyRequestRepository.save(it)
            modifySchedule(ScheduleModifyCommand.of(it))

            notificationCommandService.sendFcmNotification(FcmNotificationSendCommand(
                    memberNo = it.requestAccountNo,
                    title = "요청 수락",
                    body = "수정 요청한 일정을 상대방이 수락했습니다",
                    uri = "/calendar/${it.scheduleAt}"
            ))
            notificationCommandService.createNotification(NotificationCreateCommand(
                    historyType = NotificationHistoryType.SCHEDULE_MODIFY_REQUEST_ACCEPTED,
                    accountNo = it.requestAccountNo,
                    message = "수정 요청한 일정을 상대방이 수락했습니다",
                    path = "/calendar/${it.scheduleAt}"
            ))
        }
    }

    suspend fun rejectScheduleModifyRequest(no: Int) {
        scheduleModifyRequestRepository.findByIdOrNull(no)?.also {
            it.reject()
            scheduleModifyRequestRepository.save(it)

            notificationCommandService.sendFcmNotification(FcmNotificationSendCommand(
                    memberNo = it.requestAccountNo,
                    title = "요청 거절",
                    body = "수정 요청을 상대방이 거절했습니다",
                    uri = "/calendar/${it.scheduleAt}"
            ))
            notificationCommandService.createNotification(NotificationCreateCommand(
                    historyType = NotificationHistoryType.SCHEDULE_MODIFY_REQUEST_ACCEPTED,
                    accountNo = it.requestAccountNo,
                    message = "수정 요청을 상대방이 거절했습니다",
                    path = "/calendar/${it.scheduleAt}"
            ))
        }
    }
}
