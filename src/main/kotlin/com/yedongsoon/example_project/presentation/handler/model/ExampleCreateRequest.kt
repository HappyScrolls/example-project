package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.application.example.model.ExampleCreateCommand

data class ExampleCreateRequest(
        private val name:String,
        private val age:Int,
){

    fun toCommand() = ExampleCreateCommand(
            name = name,
            age = age,
    )

}