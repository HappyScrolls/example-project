package com.yedongsoon.example_project.infrastructure.couple

import com.yedongsoon.example_project.application.couple.CoupleService
import com.yedongsoon.example_project.application.couple.model.CoupleDetailResponse
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class CoupleAdapter(private val webClient: WebClient
) : CoupleService {

    @Value("\${couple.service.url}")
    lateinit var coupleServiceUrl: String

    override suspend fun getCoupleDetail(memberHeader: String): CoupleDetailResponse {
        return webClient.get()
                .uri("$coupleServiceUrl/couple/lover")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("Member-Code", memberHeader)
                .retrieve()
                .bodyToMono<CoupleDetailResponse>()
                .awaitSingle()
    }

}