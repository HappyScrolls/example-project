package com.yedongsoon.example_project.application.example

import com.yedongsoon.example_project.application.exception.ExampleNotFoundException
import com.yedongsoon.example_project.domain.example.ErrLog
import com.yedongsoon.example_project.domain.example.ErrLogRepository
import org.springframework.stereotype.Service

@Service
class ExampleQueryService(
        private val errLogRepository: ErrLogRepository,
) {
    suspend fun getExampleInfo(exampleInfoNo: Int, memberHeader: String): ErrLog {
        return errLogRepository.findByNo(exampleInfoNo) ?: throw ExampleNotFoundException("없음")

    }
    
}