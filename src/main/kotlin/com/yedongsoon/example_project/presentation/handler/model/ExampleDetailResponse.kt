package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.domain.example.Example

data class ExampleDetailResponse(
        val name: String,
        val age: Int,
) {
    companion object {
        fun from(example: Example) = ExampleDetailResponse(
                name = example.name,
                age = example.age
        )
    }
}