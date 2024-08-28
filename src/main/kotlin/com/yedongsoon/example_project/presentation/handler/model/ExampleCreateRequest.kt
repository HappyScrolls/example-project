package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.application.example.model.ExampleCreateCommand

data class ExampleCreateRequest(
        val age: Int,
) {

    fun toCommand(name: String) = ExampleCreateCommand(
            name = name,
            age = age,
    )

}