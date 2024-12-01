package com.yedongsoon.example_project.domain.schedule.model

import java.time.LocalDateTime

data class ScheduleCreateCommand(
        val accountNo: Int,
        val busyLevel: String,
        val scheduleName: String,
        val scheduleLocation: String,
        val scheduleWith: String,
        val groupGenderType: String,
        val scheduleStartAt: LocalDateTime,
        val scheduleEndAt: LocalDateTime,
        val isCommon: Boolean,
)
