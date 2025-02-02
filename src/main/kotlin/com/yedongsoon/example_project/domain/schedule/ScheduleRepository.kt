package com.yedongsoon.example_project.domain.schedule

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.time.LocalDateTime

interface ScheduleRepository : JpaRepository<Schedule, Int>, ScheduleRepositoryCustom {
    // 특정 일정 조회
    fun findByAccountNoAndScheduleAtAndIsCommonIsFalse(accountNo: Int, scheduleAt: LocalDate): List<Schedule>
    fun findByAccountNoInAndScheduleAtAndIsCommonIsTrue(accountNos: List<Int>, scheduleAt: LocalDate): List<Schedule>

    // 일정 상세 조회
    fun findByScheduleNo(scheduleNo: Int): Schedule?
    fun findAllByScheduleStartAt(scheduleStartAt: LocalDateTime): List<Schedule>

}
