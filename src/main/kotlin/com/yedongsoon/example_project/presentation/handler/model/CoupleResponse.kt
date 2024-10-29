package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.domain.couple.Couple
import java.time.LocalDate

data class CoupleResponse(
        val no: Int = 0,
        val accountNoA: Int,
        val accountNoB: Int,
        val nickNameA: String?,
        val nickNameB: String?,
        val name: String?,
        val startedAt: LocalDate?,
        val coupleImg: String?,
) {
    companion object {
        fun from(couple: Couple) = CoupleResponse(
                no = couple.no,
                accountNoA = couple.accountNoA,
                accountNoB = couple.accountNoB,
                nickNameA = couple.nickNameA,
                nickNameB = couple.nickNameB,
                name = couple.name,
                startedAt = couple.startedAt,
                coupleImg = couple.coupleImg,
        )
    }
}