package com.yedongsoon.example_project.domain.schedule

import java.time.LocalDateTime

interface ScheduleRepositoryCustom {
    fun existsByDuplicateSchedule(
            scheduleEndAt: LocalDateTime,
            scheduleStartAt: LocalDateTime,
            accountNo: Int,
            partnerNo: Int,
    ): Boolean

    fun existsByDuplicateScheduleForModify(
            scheduleEndAt: LocalDateTime,
            scheduleStartAt: LocalDateTime,
            accountNo: Int,
            partnerNo: Int,
            scheduleNo: Int,
    ): Boolean
}
