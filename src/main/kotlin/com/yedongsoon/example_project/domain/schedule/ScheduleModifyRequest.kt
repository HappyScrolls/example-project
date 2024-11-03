package com.yedongsoon.example_project.domain.schedule

import com.yedongsoon.example_project.domain.extension.BooleanToYNConverter
import com.yedongsoon.example_project.domain.schedule.model.ScheduleModifyRequestCreateCommand
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "schedule_modify_request")
class ScheduleModifyRequest(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "schedule_modify_request_no")
        val no: Int = 0,

        @Column(name = "account_no")
        val accountNo: Int,

        @Column(name = "schedule_no")
        val scheduleNo: Int,

        @Column(name = "request_account_no")
        val requestAccountNo: Int,

        @Column(name = "schedule_busy_level")
        val busyLevel: String,

        @Column(name = "schedule_name")
        val scheduleName: String,

        @Column(name = "schedule_location")
        val scheduleLocation: String,

        @Column(name = "schedule_with")
        val scheduleWith: String,

        @Column(name = "group_gender_type")
        val groupGenderType: String,

        @Column(name = "schedule_start_at")
        val scheduleStartAt: LocalDateTime,

        @Column(name = "schedule_end_at")
        val scheduleEndAt: LocalDateTime,

        @Convert(converter = BooleanToYNConverter::class)
        @Column(name = "is_common")
        val isCommon: Boolean,

        @Column(name = "schedule_at")
        val scheduleAt: LocalDate,
) {
    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "deleted_yn")
    var isDeleted: Boolean = false
        private set

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()

    companion object {
        // 일정 등록 (command -> Schedule)
        fun create(command: ScheduleModifyRequestCreateCommand, accountNo: Int) = ScheduleModifyRequest(
                accountNo = accountNo,
                scheduleNo = command.scheduleNo,
                requestAccountNo = command.requestAccountNo,
                busyLevel = command.busyLevel,
                scheduleName = command.scheduleName,
                scheduleLocation = command.scheduleLocation,
                scheduleWith = command.scheduleWith,
                groupGenderType = command.groupGenderType,
                scheduleStartAt = command.scheduleStartAt,
                scheduleEndAt = command.scheduleEndAt,
                isCommon = command.isCommon,
                scheduleAt = command.scheduleAt
        )
    }
}
