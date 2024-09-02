package com.yedongsoon.example_project.application.couple

import com.yedongsoon.example_project.application.couple.model.CoupleDetailResponse

interface CoupleService {
    suspend fun getCoupleDetail(memberHeader: String): CoupleDetailResponse
}