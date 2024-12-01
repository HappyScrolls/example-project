package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.domain.schedule.model.ScheduleModifyCommand
import java.time.LocalDateTime

data class ScheduleModifyRequest(
        val modifyRequestNo: Int? = null,

        val busyLevel: String,
        val scheduleName: String,

        val scheduleLocation: String,

        val scheduleWith: String,

        val groupGenderType: String,

        val scheduleStartAt: LocalDateTime,

        val scheduleEndAt: LocalDateTime,

        val isCommon: Boolean,
) {
    fun toCommand(scheduleNo: Int, accountNo: Int) = ScheduleModifyCommand(
            accountNo = accountNo,
            modifyRequestNo = modifyRequestNo,
            scheduleNo = scheduleNo,
            busyLevel = busyLevel,
            scheduleName = scheduleName,
            scheduleLocation = scheduleLocation,
            scheduleWith = scheduleWith,
            groupGenderType = groupGenderType,
            scheduleStartAt = scheduleStartAt,
            scheduleEndAt = scheduleEndAt,
            isCommon = isCommon,
    )
}
