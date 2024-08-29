package com.yedongsoon.example_project.application.schedule

import com.yedongsoon.example_project.application.schedule.model.ScheduleCreateCommand
import com.yedongsoon.example_project.domain.schedule.Schedule
import com.yedongsoon.example_project.domain.schedule.ScheduleRepository
import org.springframework.stereotype.Service

@Service
class ScheduleCommandService(
    private val scheduleRepository: ScheduleRepository
) {
    // 일정 등록
    fun createSchedule(command: ScheduleCreateCommand){
        scheduleRepository.save(Schedule.create(command))
    }

}