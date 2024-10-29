package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.domain.member.model.CoupleInfoModifyCommand
import java.time.LocalDate

data class CoupleInfoModifyRequest(
        val name: String?,
        val nickNameA: String?,
        val nickNameB: String?,
        val startedAt: LocalDate?,
        val coupleImg: String?,
) {
    fun toCommand() = CoupleInfoModifyCommand(
            name = name,
            nickNameA = nickNameA,
            nickNameB = nickNameB,
            startedAt = startedAt,
            coupleImg = coupleImg,
    )
}