package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.application.schedule.model.ScheduleCreateCommand
import java.time.LocalDate
import java.time.LocalDateTime

data class ScheduleCreateRequest(
        val busyLevel: String,
        val scheduleName: String,
        val scheduleLocation: String,
        val scheduleWith: String,
        val groupGenderType: String,
        val scheduleStartAt: LocalDateTime,
        val scheduleEndAt: LocalDateTime,
        val isCommon: Boolean,
        val scheduleAt: LocalDate,
) {
    // 입력값 -> command
    fun toCommand(no: Int) = ScheduleCreateCommand(
            accountNo = no,
            busyLevel = busyLevel,
            scheduleName = scheduleName,
            scheduleLocation = scheduleLocation,
            scheduleWith = scheduleWith,
            groupGenderType = groupGenderType,
            scheduleStartAt = scheduleStartAt,
            scheduleEndAt = scheduleEndAt,
            isCommon = isCommon,
            scheduleAt = scheduleAt
    )
}

