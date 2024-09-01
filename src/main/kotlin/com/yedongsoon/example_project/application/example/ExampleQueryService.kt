package com.yedongsoon.example_project.application.example

import com.yedongsoon.example_project.application.couple.CoupleService
import com.yedongsoon.example_project.domain.example.Example
import com.yedongsoon.example_project.domain.example.ExampleRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class ExampleQueryService(
        private val exampleRepository: ExampleRepository,
        private val coupleService: CoupleService,
) {
    suspend fun getExampleInfo(exampleInfoNo: Int, memberHeader: String): Example {
        val coupleDetail = coupleService.getCoupleDetail(memberHeader)
        println(coupleDetail)
        return exampleRepository.findByNo(exampleInfoNo) ?: throw NotFoundException()

    }
}