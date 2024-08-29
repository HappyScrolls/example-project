package com.yedongsoon.example_project.domain.schedule

import com.yedongsoon.example_project.application.schedule.model.ScheduleCreateCommand
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

    @Column(name = "schedule_at")
    var scheduleAt: LocalDate
) {
    companion object {
        // 일정 등록 (command -> Schedule)
        fun create(command: ScheduleCreateCommand) = Schedule(
            accountNo = command.accountNo,
            scheduleName = command.scheduleName,
            scheduleLocation = command.scheduleLocation,
            scheduleWith = command.scheduleWith,
            groupGenderType = command.groupGenderType,
            scheduleStartAt = command.scheduleStartAt,
            scheduleEndAt = command.scheduleEndAt,
            scheduleAt = command.scheduleAt
        )
    }
}