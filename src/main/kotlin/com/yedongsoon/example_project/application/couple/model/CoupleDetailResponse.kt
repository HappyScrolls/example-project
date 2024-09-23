package com.yedongsoon.example_project.application.couple.model

import java.time.LocalDate

data class CoupleDetailResponse(
        val no: Int,
        val accountNoA: Int,
        val accountNoB: Int,
        val name: String?,
        val startedAt: LocalDate?,
        val coupleImg: String?,
)