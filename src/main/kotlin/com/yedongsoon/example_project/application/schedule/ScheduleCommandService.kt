package com.yedongsoon.example_project.application.schedule

import com.yedongsoon.example_project.application.couple.CoupleQueryService
import com.yedongsoon.example_project.application.exception.ScheduleDuplicatedException
import com.yedongsoon.example_project.application.exception.ScheduleNotFoundException
import com.yedongsoon.example_project.domain.schedule.Schedule
import com.yedongsoon.example_project.domain.schedule.ScheduleModifyRequest
import com.yedongsoon.example_project.domain.schedule.ScheduleModifyRequestRepository
import com.yedongsoon.example_project.domain.schedule.ScheduleRepository
import com.yedongsoon.example_project.domain.schedule.model.ScheduleCreateCommand
import com.yedongsoon.example_project.domain.schedule.model.ScheduleModifyRequestCreateCommand
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ScheduleCommandService(
        private val scheduleRepository: ScheduleRepository,
        private val coupleQueryService: CoupleQueryService,
        private val scheduleModifyRequestRepository: ScheduleModifyRequestRepository,
) {
    // 일정 등록
    fun createSchedule(command: ScheduleCreateCommand) {
        val partnerNo = coupleQueryService.getLover(command.accountNo).no
        if (scheduleRepository.existsByDuplicateSchedule(command.scheduleStartAt, command.scheduleEndAt, command.accountNo, partnerNo)) {
            throw ScheduleDuplicatedException("시간대에 겹치는 일정이 존재합니다")
        }
        scheduleRepository.save(Schedule.create(command))
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
}