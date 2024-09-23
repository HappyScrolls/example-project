package com.yedongsoon.example_project.application.couple

import com.yedongsoon.example_project.application.couple.model.CoupleDetailResponse
import com.yedongsoon.example_project.application.couple.model.CouplePartnerResponse

interface CoupleService {
    suspend fun getCoupleDetail(memberHeader: String): CoupleDetailResponse
    suspend fun getCouplePartnerInfo(memberHeader: String) : CouplePartnerResponse
}