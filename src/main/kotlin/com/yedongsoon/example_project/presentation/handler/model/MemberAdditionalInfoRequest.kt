package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.domain.member.model.MemberAdditionalInfoCommand
import java.time.LocalDate


data class MemberAdditionalInfoRequest(
        val name: String?,
        val mobileNo: String,
        val birthDate: LocalDate,
        val profilePhoto: String?,
        //추후 개인정보 동의 여부 추가
) {
    fun toCommand(no: Int) = MemberAdditionalInfoCommand(
            memberNo = no,
            name = name,
            mobileNo = mobileNo,
            birthDate = birthDate,
            profilePhoto = profilePhoto,
    )
}