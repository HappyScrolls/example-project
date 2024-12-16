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
                title = "일정 등록 알림",
                body = "상대방이 일정을 등록했씁니다.",
                uri = "/calendar/${schedule.scheduleAt}"
        ))
        notificationCommandService.createNotification(NotificationCreateCommand(
                historyType = NotificationHistoryType.SCHEDULE_CREATE,
                accountNo = partnerNo,
                message = "상대방이 일정을 등록했습니다.",
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
        val schedule = scheduleRepository.findByIdOrNull(command.scheduleNo)
                ?: throw ScheduleNotFoundException("스케줄을 찾을 수 없습니다.")
        scheduleModifyRequestRepository.findByIdOrNull(command.scheduleNo)?.let { scheduleModifyRequestRepository.delete(it) }
        scheduleModifyRequestRepository.save(ScheduleModifyRequest.create(command, schedule.accountNo))

        notificationCommandService.sendFcmNotification(FcmNotificationSendCommand(
                memberNo = schedule.accountNo,
                title = "일정 등록 알림",
                body = "상대방이 일정을 등록했씁니다.",
                uri = "/edit-schedule/${schedule.scheduleNo}"
        ))
        notificationCommandService.createNotification(NotificationCreateCommand(
                historyType = NotificationHistoryType.SCHEDULE_MODIFY_REQUEST,
                accountNo = schedule.accountNo,
                message = "일정 수정을 요청했습니다.",
                path = "/edit-schedule/${schedule.scheduleNo}"
        ))
    }

    suspend fun getScheduleModifyRequest(no: Int): ScheduleModifyRequest {
        return scheduleModifyRequestRepository.findByScheduleNoAndIsAcceptedFalse(no)
                ?: throw ScheduleModifyRequestNotFoundException("수정 요청이 존재하지 않습니다")
    }

    suspend fun modifySchedule(command: ScheduleModifyCommand) {
        val (schedule, partnerNo) = scheduleRepository.findByIdOrNull(command.scheduleNo)?.let {
            val partnerNo = coupleQueryService.getLover(command.accountNo).no
            if (scheduleRepository.existsByDuplicateScheduleForModify(command.scheduleStartAt, command.scheduleEndAt, command.accountNo, partnerNo, command.scheduleNo)) {
                throw ScheduleDuplicatedException("시간대에 겹치는 일정이 존재합니다")
            }
            it.updateSchedule(command)
            Pair(scheduleRepository.save(it), partnerNo)
        } ?: throw ScheduleNotFoundException("존재하지 않습니다")

        scheduleModifyRequestRepository.findByIdOrNull(command.scheduleNo)?.also {
            it.accept()
            scheduleModifyRequestRepository.save(it)
            notificationCommandService.sendFcmNotification(FcmNotificationSendCommand(
                    memberNo = partnerNo,
                    title = "일정 수정 알림",
                    body = "수정 요청한 일정을 상대방이 수정했습니다",
                    uri = "/calendar/${schedule.scheduleAt}"
            ))
            notificationCommandService.createNotification(NotificationCreateCommand(
                    historyType = NotificationHistoryType.SCHEDULE_MODIFY_REQUEST_ACCEPTED,
                    accountNo = partnerNo,
                    message = "수정 요청한 일정을 상대방이 수정했습니다",
                    path = "/calendar/${schedule.scheduleAt}"
            ))
        }
    }
}
