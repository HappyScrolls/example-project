package com.yedongsoon.example_project.domain.schedule.model

import java.time.LocalDate
import java.time.LocalDateTime

data class ScheduleModifyRequestCreateCommand(
        val scheduleNo: Int,
        val requestAccountNo: Int,
        val busyLevel: String,
        val scheduleName: String,
        val scheduleLocation: String,
        val scheduleWith: String,
        val groupGenderType: String,
        val scheduleStartAt: LocalDateTime,
        val scheduleEndAt: LocalDateTime,
        val isCommon: Boolean,
        val scheduleAt: LocalDate,
)