package com.yedongsoon.example_project.domain.member.model

import java.time.LocalDate

data class MemberInfoModifyCommand(
        val memberNo: Int,
        val name: String?,
        val email: String?,
        val profilePhoto: String?,
        val birthDate: LocalDate?,
        val mobileNo: String?,
)