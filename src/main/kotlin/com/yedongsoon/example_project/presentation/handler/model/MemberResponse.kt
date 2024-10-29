package com.yedongsoon.example_project.presentation.handler.model

import com.yedongsoon.example_project.domain.member.Member
import java.time.LocalDate

data class MemberResponse(
        val no: Int = 0,
        val accountId: String?,
        val name: String?,
        val email: String?,
        val profilePhoto: String?,
        val birthDate: LocalDate?,
        val mobileNo: String?,
) {
    companion object {
        fun from(member: Member) = MemberResponse(
                no = member.no,
                accountId = member.accountId,
                name = member.name,
                email = member.email,
                profilePhoto = member.profilePhoto,
                birthDate = member.birthDate,
                mobileNo = member.mobileNo,
        )
    }
}