package com.yedongsoon.example_project.infrastructure.couple

import com.yedongsoon.example_project.application.couple.CoupleService
import com.yedongsoon.example_project.application.couple.model.CoupleDetailResponse
import com.yedongsoon.example_project.application.couple.model.CouplePartnerResponse
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.LoggerFactory
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
    private val logger = LoggerFactory.getLogger(CoupleAdapter::class.java)

    override suspend fun getCoupleDetail(memberHeader: String): CoupleDetailResponse {
        return try {
            webClient.get()
                    .uri("$coupleServiceUrl/account-service/couple/detail")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .header("Member-Code", memberHeader)
                    .retrieve()
                    .bodyToMono<CoupleDetailResponse>()
                    .awaitSingle()
        } catch (e: Exception) {
            logger.error("Error while fetching couple details for Member-Code: $memberHeader", e)
            throw e
        }
    }

    // 내 상대방 조회
    override suspend fun getCouplePartnerInfo(memberHeader: String): CouplePartnerResponse {
        return try {
            webClient.get()
                    .uri("$coupleServiceUrl/account-service/couple/lover")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .header("Member-Code", memberHeader)
                    .retrieve()
                    .bodyToMono<CouplePartnerResponse>()
                    .awaitSingle()
        } catch (e: Exception) {
            logger.error("Error while fetching couple partner details for Member-Code: $memberHeader", e)
            throw e
        }
    }

}