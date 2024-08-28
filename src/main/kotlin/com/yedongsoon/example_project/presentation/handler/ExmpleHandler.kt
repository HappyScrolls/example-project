package com.yedongsoon.example_project.presentation.handler

import com.yedongsoon.example_project.application.example.ExampleCommandService
import com.yedongsoon.example_project.application.example.ExampleQueryService
import com.yedongsoon.example_project.presentation.extension.extractMemberCodeHeader
import com.yedongsoon.example_project.presentation.extension.intQueryParam
import com.yedongsoon.example_project.presentation.handler.model.ExampleCreateRequest
import com.yedongsoon.example_project.presentation.handler.model.ExampleDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.*


@Service
class ExampleHandler(
        private val exampleCommandService: ExampleCommandService,
        private val exampleQueryService: ExampleQueryService,
) {
    suspend fun createExample(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val memberHeader = request.extractMemberCodeHeader()
        val command = request.awaitBodyOrNull<ExampleCreateRequest>()?.toCommand(memberHeader.name)
                ?: throw IllegalArgumentException()

        exampleCommandService.createExample(command)
        ServerResponse.noContent().buildAndAwait()
    }

    suspend fun getExample(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val memberHeader = request.extractMemberCodeHeader()
        val exampleInfoNo = request.intQueryParam("exampleInfoNo")
        val result = exampleQueryService.getExampleInfo(memberHeader.name, exampleInfoNo)
        ServerResponse.ok().bodyValueAndAwait(ExampleDetailResponse.from(result))
    }
}