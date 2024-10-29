package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.domain.schedule.model.ScheduleCreateCommand
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalDateTime

data class ScheduleCreateRequest(
        @Schema(description = "바쁨 레벨", example = "HIGH")
        val busyLevel: String,
        @Schema(description = "일정 이름", example = "팀 회의")
        val scheduleName: String,

        @Schema(description = "일정 장소", example = "회의실 A")
        val scheduleLocation: String,

        @Schema(description = "일정을 함께하는 사람", example = "동료들")
        val scheduleWith: String,

        @Schema(description = "그룹 성별 유형", example = "FEMALE")
        val groupGenderType: String,

        @Schema(description = "일정 시작 시간", example = "2023-10-28T14:00:00")
        val scheduleStartAt: LocalDateTime,

        @Schema(description = "일정 종료 시간", example = "2023-10-28T15:00:00")
        val scheduleEndAt: LocalDateTime,

        @Schema(description = "공통 일정 여부", example = "true")
        val isCommon: Boolean,

        @Schema(description = "일정 날짜", example = "2023-10-28")
        val scheduleAt: LocalDate
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

