package com.yedongsoon.example_project.infrastructure.jpa

import com.querydsl.core.BooleanBuilder
import com.yedongsoon.example_project.domain.schedule.QSchedule
import com.yedongsoon.example_project.domain.schedule.ScheduleRepositoryCustom
import com.yedongsoon.example_project.infrastructure.config.QuerydslSupport
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ScheduleRepositoryImpl : QuerydslSupport(QSchedule::class.java), ScheduleRepositoryCustom {
    override fun existsByDuplicateSchedule(scheduleEndAt: LocalDateTime, scheduleStartAt: LocalDateTime, accountNo: Int, partnerNo: Int): Boolean {
        val schedule = QSchedule.schedule
        val conditionBuilder = BooleanBuilder()
        conditionBuilder.and(schedule.isDeleted.isFalse)
        conditionBuilder.and(schedule.scheduleStartAt.loe(scheduleStartAt))
        conditionBuilder.and(schedule.scheduleEndAt.goe(scheduleEndAt))
        conditionBuilder.and(schedule.accountNo.eq(accountNo))

        val partnerConditionBuilder = BooleanBuilder()
        partnerConditionBuilder.and(schedule.isDeleted.isFalse)
        partnerConditionBuilder.and(schedule.scheduleStartAt.loe(scheduleStartAt))
        partnerConditionBuilder.and(schedule.scheduleEndAt.goe(scheduleEndAt))
        partnerConditionBuilder.and(schedule.accountNo.eq(partnerNo))
        partnerConditionBuilder.and(schedule.isCommon.isTrue)
        val result = from(schedule)
                .where(conditionBuilder.or(partnerConditionBuilder))
                .fetchCount()
        return result != 0L
    }

    override fun existsByDuplicateScheduleForModify(scheduleEndAt: LocalDateTime, scheduleStartAt: LocalDateTime, accountNo: Int, partnerNo: Int, scheduleNo: Int): Boolean {
        val schedule = QSchedule.schedule
        val conditionBuilder = BooleanBuilder()
        conditionBuilder.and(schedule.isDeleted.isFalse)
        conditionBuilder.and(schedule.scheduleStartAt.loe(scheduleStartAt))
        conditionBuilder.and(schedule.scheduleEndAt.goe(scheduleEndAt))
        conditionBuilder.and(schedule.accountNo.eq(accountNo))
        conditionBuilder.and(schedule.scheduleNo.ne(scheduleNo))

        val partnerConditionBuilder = BooleanBuilder()
        partnerConditionBuilder.and(schedule.isDeleted.isFalse)
        partnerConditionBuilder.and(schedule.scheduleStartAt.loe(scheduleStartAt))
        partnerConditionBuilder.and(schedule.scheduleEndAt.goe(scheduleEndAt))
        partnerConditionBuilder.and(schedule.accountNo.eq(partnerNo))
        partnerConditionBuilder.and(schedule.isCommon.isTrue)
        val result = from(schedule)
                .where(conditionBuilder.or(partnerConditionBuilder))
                .fetchCount()
        return result != 0L
    }
}
