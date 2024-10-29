package com.yedongsoon.example_project.domain.member.model

data class CoupleCreateCommand(
        val inviteCode: String,
        val accountNoB: Int,
)