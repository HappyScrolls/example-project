package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.domain.schedule.ScheduleModifyRequest
import java.time.LocalDateTime

data class ScheduleModifyRequestResponse(
        val scheduleNo: Int,
        val busyLevel: String,
        val scheduleName: String,
        val scheduleLocation: String,
        val scheduleWith: String,
        val groupGenderType: String,
        val scheduleStartAt: LocalDateTime,
        val scheduleEndAt: LocalDateTime,
        val isCommon: Boolean,
) {
    companion object {
        fun of(summary: ScheduleModifyRequest) = ScheduleModifyRequestResponse(
                scheduleNo = summary.scheduleNo,
                busyLevel = summary.busyLevel,
                scheduleName = summary.scheduleName,
                scheduleLocation = summary.scheduleLocation,
                scheduleWith = summary.scheduleWith,
                groupGenderType = summary.groupGenderType,
                scheduleStartAt = summary.scheduleStartAt,
                scheduleEndAt = summary.scheduleEndAt,
                isCommon = summary.isCommon,
        )
    }
}
