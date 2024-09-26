package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.domain.schedule.Schedule
import java.time.LocalDateTime

data class ScheduleDetailResponse(
        val scheduleNo: Int,
        val accountNo: Int,
        val busyLevel: String,
        val scheduleName: String,
        val scheduleLocation: String,
        val scheduleWith: String,
        val status: String,
        val scheduleStartAt: LocalDateTime,
        val scheduleEndAt: LocalDateTime,
        val isCommon: Boolean,
) {
    companion object {
        // 특정 일정 조회 (엔티티 -> response)
        fun from(schedule: Schedule) = ScheduleDetailResponse(
                scheduleNo = schedule.scheduleNo,
                accountNo = schedule.accountNo,
                busyLevel = schedule.busyLevel,
                scheduleName = schedule.scheduleName,
                scheduleLocation = schedule.scheduleLocation,
                scheduleWith = schedule.scheduleWith,
                status = schedule.status,
                scheduleStartAt = schedule.scheduleStartAt,
                scheduleEndAt = schedule.scheduleEndAt,
                isCommon = schedule.isCommon,
        )
    }
}
