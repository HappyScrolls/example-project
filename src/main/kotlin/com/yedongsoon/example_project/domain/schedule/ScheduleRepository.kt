package com.yedongsoon.example_project.domain.schedule

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface ScheduleRepository : JpaRepository<Schedule, Int> {
    // 특정 일정 조회
    fun findByAccountNoAndScheduleAt(accountNo: Int, scheduleAt: LocalDate): List<Schedule>

    // 일정 상세 조회
    fun findByAccountNoAndScheduleNo(accountNo: Int, scheduleNo : Int) : Schedule
}