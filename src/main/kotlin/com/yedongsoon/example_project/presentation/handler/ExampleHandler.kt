package com.yedongsoon.example_project.presentation.handler

import com.yedongsoon.example_project.application.example.ExampleCommandService
import com.yedongsoon.example_project.application.example.ExampleQueryService
import com.yedongsoon.example_project.presentation.handler.model.ExampleCreateRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBodyOrNull
import org.springframework.web.reactive.function.server.buildAndAwait


@Service
class ExampleHandler(
        private val exampleCommandService: ExampleCommandService,
        private val exampleQueryService: ExampleQueryService,
) {
    suspend fun createExample(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val command = request.awaitBodyOrNull<ExampleCreateRequest>()?.toCommand()
                ?: throw IllegalArgumentException()

        exampleCommandService.createExample(command)
        ServerResponse.noContent().buildAndAwait()
    }


}