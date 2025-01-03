package com.yedongsoon.example_project.domain.schedule

import com.yedongsoon.example_project.domain.extension.BooleanToYNConverter
import com.yedongsoon.example_project.domain.schedule.model.ScheduleCreateCommand
import com.yedongsoon.example_project.domain.schedule.model.ScheduleModifyCommand
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "schedule")
class Schedule(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "schedule_no")
        val scheduleNo: Int = 0,

        @Column(name = "account_no")
        val accountNo: Int,

        @Column(name = "schedule_busy_level")
        var busyLevel: String,

        @Column(name = "schedule_name")
        var scheduleName: String,

        @Column(name = "schedule_location")
        var scheduleLocation: String,

        @Column(name = "schedule_with")
        var scheduleWith: String,

        @Column(name = "group_gender_type")
        var groupGenderType: String,

        @Column(name = "schedule_start_at")
        var scheduleStartAt: LocalDateTime,

        @Column(name = "schedule_end_at")
        var scheduleEndAt: LocalDateTime,

        isCommon: Boolean,

        @Column(name = "schedule_at")
        var scheduleAt: LocalDate,

        ) {

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "is_common")
    var isCommon: Boolean = isCommon
        private set

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "deleted_yn")
    var isDeleted: Boolean = false
        private set

    @Column(name = "status")
    var status: String = "예정"
        private set

    fun changeStatus(status: String) {
        this.status = status
    }

    fun toCommon() {
        this.isCommon = true
    }

    fun updateSchedule(command: ScheduleModifyCommand) {
        this.busyLevel = command.busyLevel
        this.scheduleName = command.scheduleName
        this.scheduleLocation = command.scheduleLocation
        this.scheduleWith = command.scheduleWith
        this.groupGenderType = command.groupGenderType
        this.scheduleStartAt = command.scheduleStartAt
        this.scheduleEndAt = command.scheduleEndAt
        this.isCommon = command.isCommon
        this.scheduleAt = command.scheduleStartAt.toLocalDate()
    }

    companion object {
        // 일정 등록 (command -> Schedule)
        fun create(command: ScheduleCreateCommand) = Schedule(
                accountNo = command.accountNo,
                busyLevel = command.busyLevel,
                scheduleName = command.scheduleName,
                scheduleLocation = command.scheduleLocation,
                scheduleWith = command.scheduleWith,
                groupGenderType = command.groupGenderType,
                scheduleStartAt = command.scheduleStartAt,
                scheduleEndAt = command.scheduleEndAt,
                isCommon = command.isCommon,
                scheduleAt = command.scheduleStartAt.toLocalDate()
        )
    }
}
