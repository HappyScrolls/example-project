package com.yedongsoon.example_project.application.example

import com.yedongsoon.example_project.application.exception.ExampleNotFoundException
import com.yedongsoon.example_project.domain.example.Example
import com.yedongsoon.example_project.domain.example.ExampleRepository
import org.springframework.stereotype.Service

@Service
class ExampleQueryService(
        private val exampleRepository: ExampleRepository,
) {
    suspend fun getExampleInfo(exampleInfoNo: Int, memberHeader: String): Example {
        return exampleRepository.findByNo(exampleInfoNo) ?: throw ExampleNotFoundException("없음")

    }


}