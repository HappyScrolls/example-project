package com.yedongsoon.example_project.presentation.handler

import com.yedongsoon.example_project.application.schedule.ScheduleCommandService
import com.yedongsoon.example_project.presentation.extension.extractMemberCodeHeader
import com.yedongsoon.example_project.presentation.handler.model.ScheduleCreateRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.commons.logging.Log
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBodyOrNull
import org.springframework.web.reactive.function.server.buildAndAwait
import java.lang.IllegalArgumentException

@Service
class ScheduleHandler (
    private val scheduleCommandService: ScheduleCommandService
) {
    // 일정 등록
    suspend fun createSchedule(request: ServerRequest) : ServerResponse = withContext(Dispatchers.IO){
        val memberHeader = request.extractMemberCodeHeader()
        // 요청 body -> ScheduleCreateRequest -> ScheduleCreateCommand
        val command = request.awaitBodyOrNull<ScheduleCreateRequest>()
            ?.toCommand(memberHeader.no)
            ?: throw IllegalArgumentException()

        scheduleCommandService.createSchedule(command)
        ServerResponse.noContent().buildAndAwait()
    }
}