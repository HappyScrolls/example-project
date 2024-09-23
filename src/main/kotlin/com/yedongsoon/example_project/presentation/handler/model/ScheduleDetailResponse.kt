package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.domain.schedule.Schedule
import java.time.LocalDateTime

data class ScheduleDetailResponse(
    val scheduleName: String,
    val scheduleStartAt: LocalDateTime,
    val scheduleEndAt: LocalDateTime
) {
    companion object {
        // 특정 일정 조회 (엔티티 -> response)
        fun from(schedule: Schedule) = ScheduleDetailResponse(
            scheduleName = schedule.scheduleName,
            scheduleStartAt = schedule.scheduleStartAt,
            scheduleEndAt = schedule.scheduleEndAt
        )
    }
}
