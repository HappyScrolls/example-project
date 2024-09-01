package com.yedongsoon.example_project.application.example

import com.yedongsoon.example_project.application.couple.CoupleService
import com.yedongsoon.example_project.application.exception.ExampleNotFoundException
import com.yedongsoon.example_project.domain.example.Example
import com.yedongsoon.example_project.domain.example.ExampleRepository
import com.yedongsoon.example_project.infrastructure.couple.CoupleAdapter
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ExampleQueryService(
        private val exampleRepository: ExampleRepository,
        private val coupleService: CoupleService,
) {
    private val logger = LoggerFactory.getLogger(CoupleAdapter::class.java)
    suspend fun getExampleInfo(exampleInfoNo: Int, memberHeader: String): Example {
        val coupleDetail = coupleService.getCoupleDetail(memberHeader)
        logger.info(coupleDetail.name)

        return exampleRepository.findByNo(exampleInfoNo) ?: throw ExampleNotFoundException("없음")

    }
}