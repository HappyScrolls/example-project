package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.domain.member.model.CoupleCreateCommand


data class CoupleCreateRequest(
        val inviteCode: String,
) {
    fun toCommand(memberNo: Int) = CoupleCreateCommand(
            inviteCode = inviteCode,
            accountNoB = memberNo,
    )
}