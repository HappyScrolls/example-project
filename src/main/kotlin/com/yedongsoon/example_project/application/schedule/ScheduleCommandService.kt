package com.yedongsoon.example_project.application.schedule

import com.yedongsoon.example_project.application.couple.CoupleQueryService
import com.yedongsoon.example_project.application.exception.ScheduleDuplicatedException
import com.yedongsoon.example_project.application.exception.ScheduleNotFoundException
import com.yedongsoon.example_project.application.notification.NotificationCommandService
import com.yedongsoon.example_project.application.notification.model.FcmNotificationSendCommand
import com.yedongsoon.example_project.domain.schedule.Schedule
import com.yedongsoon.example_project.domain.schedule.ScheduleModifyRequest
import com.yedongsoon.example_project.domain.schedule.ScheduleModifyRequestRepository
import com.yedongsoon.example_project.domain.schedule.ScheduleRepository
import com.yedongsoon.example_project.domain.schedule.model.ScheduleCreateCommand
import com.yedongsoon.example_project.domain.schedule.model.ScheduleModifyCommand
import com.yedongsoon.example_project.domain.schedule.model.ScheduleModifyRequestCreateCommand
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
        scheduleRepository.save(Schedule.create(command))
        notificationCommandService.sendFcmNotification(FcmNotificationSendCommand(
                memberNo = partnerNo,
                title = "일정 등록 알림",
                body = "상대방이 일정을 등록했씁니다.",
                uri = "/main"
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

    fun createScheduleModifyRequest(command: ScheduleModifyRequestCreateCommand) {
        val accountNo = scheduleRepository.findByIdOrNull(command.scheduleNo)?.accountNo
                ?: throw ScheduleNotFoundException("스케줄을 찾을 수 없습니다.")
        scheduleModifyRequestRepository.save(ScheduleModifyRequest.create(command, accountNo))
    }

    fun modifySchedule(command: ScheduleModifyCommand) {
        scheduleRepository.findByIdOrNull(command.scheduleNo)?.let {
            it.updateSchedule(command)
            scheduleRepository.save(it)
        } ?: throw ScheduleNotFoundException("존재하지 않습니다")
        //TODO: 수정 요청 상태 변경
    }
}