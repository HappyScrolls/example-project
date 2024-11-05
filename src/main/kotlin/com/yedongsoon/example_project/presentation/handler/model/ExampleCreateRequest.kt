package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.application.example.model.ErrLogCreateCommand

data class ExampleCreateRequest(
        val content: String,
) {

    fun toCommand() = ErrLogCreateCommand(
            content = content
    )

}