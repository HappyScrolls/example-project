package com.yedongsoon.example_project.application.schedule.model

import java.time.LocalDate
import java.time.LocalDateTime

data class ScheduleCreateCommand(
    val accountNo : Int,
    val scheduleName: String,
    val scheduleLocation: String,
    val scheduleWith: String,
    val groupGenderType: String,
    val scheduleStartAt: LocalDateTime,
    val scheduleEndAt: LocalDateTime,
    val scheduleAt: LocalDate,
)
