package com.yedongsoon.example_project.domain.member.model

import java.time.LocalDate

data class MemberAdditionalInfoCommand(
        val memberNo: Int,
        val name: String?,
        val mobileNo: String,
        val birthDate: LocalDate,
        val profilePhoto: String?,
        //추후 개인정보 동의 여부 추가
)