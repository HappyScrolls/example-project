package com.yedongsoon.example_project.domain.schedule

import org.springframework.data.jpa.repository.JpaRepository

interface ScheduleModifyRequestRepository : JpaRepository<ScheduleModifyRequest, Int> {
    fun findByNoAndIsAcceptedIsNotNull(scheduleNo: Int): ScheduleModifyRequest?
}
