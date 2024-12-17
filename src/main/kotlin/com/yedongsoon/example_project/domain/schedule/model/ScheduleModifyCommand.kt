package com.yedongsoon.example_project.domain.schedule.model

import com.yedongsoon.example_project.domain.schedule.ScheduleModifyRequest
import java.time.LocalDateTime

data class ScheduleModifyCommand(
        val accountNo: Int,
        val modifyRequestNo: Int?,
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
        fun of(request: ScheduleModifyRequest) = ScheduleModifyCommand(
                accountNo = request.accountNo,
                modifyRequestNo = null,
                scheduleNo = request.scheduleNo,
                busyLevel = request.busyLevel,
                scheduleName = request.scheduleName,
                scheduleLocation = request.scheduleLocation,
                scheduleWith = request.scheduleWith,
                groupGenderType = request.groupGenderType,
                scheduleStartAt = request.scheduleStartAt,
                scheduleEndAt = request.scheduleEndAt,
                isCommon = request.isCommon,
        )
    }
}
