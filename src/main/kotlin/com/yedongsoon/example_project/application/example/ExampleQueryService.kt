package com.yedongsoon.example_project.application.example

import com.yedongsoon.example_project.domain.example.Example
import com.yedongsoon.example_project.domain.example.ExampleRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class ExampleQueryService(
        private val exampleRepository: ExampleRepository,
) {
    fun getExampleInfo(name: String, exampleInfoNo: Int): Example {
        return exampleRepository.findByNameAndNo(name, exampleInfoNo) ?: throw NotFoundException()
    }
}