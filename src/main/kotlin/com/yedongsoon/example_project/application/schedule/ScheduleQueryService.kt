package com.yedongsoon.example_project.application.schedule

import com.yedongsoon.example_project.application.couple.CoupleService
import com.yedongsoon.example_project.domain.schedule.Schedule
import com.yedongsoon.example_project.domain.schedule.ScheduleRepository
import com.yedongsoon.example_project.presentation.handler.model.ScheduleDetailResponse
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ScheduleQueryService(
    private val scheduleRepository: ScheduleRepository,
) {

    // 특정 날짜 일정 조회
    fun getScheduleByDate(accountNo: Int, searchDate: LocalDate): List<ScheduleDetailResponse> {
        val schedules: List<Schedule> = scheduleRepository.findByAccountNoAndScheduleAt(accountNo, searchDate)

        // 조회한 일정들 -> ScheduleDetailResponse
        return schedules.map { schedule -> ScheduleDetailResponse.from(schedule) }
    }

    // 일정 상세 조회
    fun getScheduleByScheduleNo(accountNo: Int, scheduleNo: Int): Schedule {
        return scheduleRepository.findByAccountNoAndScheduleNo(accountNo, scheduleNo)
    }
}