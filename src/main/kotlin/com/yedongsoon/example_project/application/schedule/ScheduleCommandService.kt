package com.yedongsoon.example_project.application.schedule

import com.yedongsoon.example_project.application.exception.ScheduleNotFoundException
import com.yedongsoon.example_project.application.schedule.model.ScheduleCreateCommand
import com.yedongsoon.example_project.domain.schedule.Schedule
import com.yedongsoon.example_project.domain.schedule.ScheduleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ScheduleCommandService(
        private val scheduleRepository: ScheduleRepository
) {
    // 일정 등록
    fun createSchedule(command: ScheduleCreateCommand) {
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
}