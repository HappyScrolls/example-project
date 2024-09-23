package com.yedongsoon.example_project.application.couple.model

data class CouplePartnerResponse(
        val no: Int,
        val accountId: String,
        val name: String,
        val email: String,
        val profilePhoto: String? = null,
        val birthDate: String?,
        val mobileNo: String?,
)
