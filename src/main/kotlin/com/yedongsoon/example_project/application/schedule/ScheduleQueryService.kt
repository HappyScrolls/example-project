package com.yedongsoon.example_project.application.schedule

import com.yedongsoon.example_project.application.exception.ScheduleNotFoundException
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
    fun getCommonScheduleByDate(accountNo: Int, loverNo: Int, searchDate: LocalDate): List<ScheduleDetailResponse> {
        val schedules: List<Schedule> = scheduleRepository.findByAccountNoInAndScheduleAtAndIsCommonIsTrue(listOf(accountNo, loverNo), searchDate)

        // 조회한 일정들 -> ScheduleDetailResponse
        return schedules.map { schedule -> ScheduleDetailResponse.from(schedule) }
    }

    fun getScheduleByDateExceptCommon(accountNo: Int, searchDate: LocalDate): List<ScheduleDetailResponse> {
        val schedules: List<Schedule> = scheduleRepository.findByAccountNoAndScheduleAtAndIsCommonIsFalse(accountNo, searchDate)

        return schedules.map { schedule -> ScheduleDetailResponse.from(schedule) }
    }

    // 일정 상세 조회
    fun getScheduleByScheduleNo(accountNo: Int, scheduleNo: Int): Schedule {
        return scheduleRepository.findByScheduleNo(scheduleNo)
                ?: throw ScheduleNotFoundException("일정을 찾을 수 없습니다")
    }

    // 일정 존재 여부
    fun existsByScheduleNo(scheduleNo: Int): Boolean {
        return scheduleRepository.existsById(scheduleNo)
    }
}
