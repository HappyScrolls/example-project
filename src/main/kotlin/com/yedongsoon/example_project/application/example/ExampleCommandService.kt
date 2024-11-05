package com.yedongsoon.example_project.application.example

import com.yedongsoon.example_project.application.example.model.ErrLogCreateCommand
import com.yedongsoon.example_project.domain.example.ErrLog
import com.yedongsoon.example_project.domain.example.ErrLogRepository
import org.springframework.stereotype.Service

@Service
class ExampleCommandService(
        private val errLogRepository: ErrLogRepository,
) {
    fun createExample(command: ErrLogCreateCommand) {
        errLogRepository.save(ErrLog.create(command))
    }
}