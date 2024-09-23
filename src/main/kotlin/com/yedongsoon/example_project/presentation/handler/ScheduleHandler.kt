package com.yedongsoon.example_project.presentation.handler

import com.yedongsoon.example_project.application.couple.CoupleService
import com.yedongsoon.example_project.application.schedule.ScheduleCommandService
import com.yedongsoon.example_project.application.schedule.ScheduleQueryService
import com.yedongsoon.example_project.presentation.extension.extractMemberCodeHeader
import com.yedongsoon.example_project.presentation.extension.extractRawMemberCodeHeader
import com.yedongsoon.example_project.presentation.extension.intPathVariable
import com.yedongsoon.example_project.presentation.extension.localDateQueryParam
import com.yedongsoon.example_project.presentation.handler.model.ScheduleCreateRequest
import com.yedongsoon.example_project.presentation.handler.model.ScheduleDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.*

@Service
class ScheduleHandler(
        private val scheduleCommandService: ScheduleCommandService,
        private val scheduleQueryService: ScheduleQueryService,
        private val coupleService: CoupleService
) {
    // 일정 등록
    suspend fun createSchedule(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val memberHeader = request.extractMemberCodeHeader()
        val command = request.awaitBodyOrNull<ScheduleCreateRequest>()
                ?.toCommand(memberHeader.no)
                ?: throw IllegalArgumentException() // TODO : 커스텀 예외 변경 필요

        scheduleCommandService.createSchedule(command)
        ServerResponse.noContent().buildAndAwait()
    }

    // 특정 날짜 일정 조회
    suspend fun readSchedules(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val memberHeader = request.extractMemberCodeHeader()
        val searchDate = request.localDateQueryParam("searchDate")
        val result = scheduleQueryService.getScheduleByDate(memberHeader.no, searchDate)
        ServerResponse.ok().bodyValueAndAwait(result)
    }

    // 일정 상세 조회
    suspend fun readScheduleDetail(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val memberHeader = request.extractMemberCodeHeader()
        val scheduleNo = request.pathVariable("scheduleNo").toIntOrNull()
                ?: throw IllegalArgumentException("Invalid or missing 'scheduleNo' path variable")
        val result = scheduleQueryService.getScheduleByScheduleNo(memberHeader.no, scheduleNo)
        ServerResponse.ok().bodyValueAndAwait(ScheduleDetailResponse.from(result))
    }

    // (커플) 특정 날짜의 일정 조회
    suspend fun readCouplePartnerSchedules(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val memberHeader = request.extractRawMemberCodeHeader()
        val couplePartnerResponse = coupleService.getCouplePartnerInfo(memberHeader)
        println("couplePartnerResponse = ${couplePartnerResponse}")
        val partnerNo = couplePartnerResponse.no
        val searchDate = request.localDateQueryParam("searchDate")

        val result = scheduleQueryService.getScheduleByDateExceptCommon(partnerNo, searchDate)

        ServerResponse.ok().bodyValueAndAwait(result)
    }

    // 일정 삭제
    suspend fun deleteSchedule(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val scheduleNo = request.intPathVariable("scheduleNo")

        val isScheduleExists = scheduleQueryService.existsByScheduleNo(scheduleNo)
        if (!isScheduleExists) {
            return@withContext ServerResponse.status(404).bodyValueAndAwait("삭제하려는 일정이 존재하지 않습니다.")
        }

        scheduleCommandService.deleteSchedule(scheduleNo)
        ServerResponse.ok().bodyValueAndAwait("일정이 성공적으로 삭제되었습니다.")
    }
}