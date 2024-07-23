package com.yedongsoon.example_project.presentation.handler

import com.yedongsoon.example_project.application.example.ExampleCommandService
import com.yedongsoon.example_project.application.example.ExampleQueryService
import com.yedongsoon.example_project.presentation.extension.intQueryParam
import com.yedongsoon.example_project.presentation.handler.model.ExampleCreateRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.*


@Service
class ExampleHandler(
        private val exampleCommandService: ExampleCommandService,
//        private val exampleQueryService: ExampleQueryService,
) {
    suspend fun createExample(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val command = request.awaitBodyOrNull<ExampleCreateRequest>()?.toCommand()
                ?: throw IllegalArgumentException()

        exampleCommandService.createExample(command)
        ServerResponse.noContent().buildAndAwait()
    }

//    suspend fun getExample(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
//        val exampleInfoNo = request.intQueryParam("exampleInfoNo")
//        val summary = exampleQueryService.getExampleInfo(serviceHeader.no, exampleInfoNo)
//        ServerResponse.ok().bodyValueAndAwait(ExampleInfoResponse.of(summary))
//    }
}