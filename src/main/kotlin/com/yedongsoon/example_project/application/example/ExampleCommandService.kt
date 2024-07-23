package com.yedongsoon.example_project.application.example

import com.yedongsoon.example_project.application.example.model.ExampleCreateCommand
import com.yedongsoon.example_project.domain.example.Example
import com.yedongsoon.example_project.domain.example.ExampleRepository
import org.springframework.stereotype.Service

@Service
class ExampleCommandService(
        private val exampleRepository: ExampleRepository,
) {
    fun createExample(command: ExampleCreateCommand) {
        exampleRepository.save(Example.create(command))
    }
}