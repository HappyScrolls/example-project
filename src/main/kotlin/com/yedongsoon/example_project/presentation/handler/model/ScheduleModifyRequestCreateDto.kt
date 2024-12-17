package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.domain.schedule.model.ScheduleModifyRequestCreateCommand
import java.time.LocalDateTime

data class ScheduleModifyRequestCreateDto(
        val scheduleNo: Int,
        val busyLevel: String,
        val scheduleName: String,
        val scheduleLocation: String,
        val scheduleWith: String,
        val genderType: String,
        val scheduleStartAt: LocalDateTime,
        val scheduleEndAt: LocalDateTime,
        val isCommon: Boolean,
) {
    fun toCommand(no: Int) = ScheduleModifyRequestCreateCommand(
            scheduleNo = scheduleNo,
            requestAccountNo = no,
            busyLevel = busyLevel,
            scheduleName = scheduleName,
            scheduleLocation = scheduleLocation,
            scheduleWith = scheduleWith,
            groupGenderType = genderType,
            scheduleStartAt = scheduleStartAt,
            scheduleEndAt = scheduleEndAt,
            isCommon = isCommon,
    )
}
