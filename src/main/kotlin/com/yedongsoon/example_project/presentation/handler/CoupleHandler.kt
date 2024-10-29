package com.yedongsoon.example_project.presentation.handler


import com.yedongsoon.example_project.application.couple.CoupleCommandService
import com.yedongsoon.example_project.application.couple.CoupleQueryService
import com.yedongsoon.example_project.presentation.extension.extractMemberCodeHeader
import com.yedongsoon.example_project.presentation.handler.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.*

@Service
class CoupleHandler(
        private val coupleQueryService: CoupleQueryService,
        private val coupleCommandService: CoupleCommandService,
) {
    suspend fun getDetail(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val memberHeader = request.extractMemberCodeHeader()

        val result = coupleQueryService.getDetail(memberHeader.no)
        ServerResponse.ok().bodyValueAndAwait(CoupleResponse.from(result))
    }

    suspend fun getLover(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val memberHeader = request.extractMemberCodeHeader()

        val result = coupleQueryService.getLover(memberHeader.no)
        ServerResponse.ok().bodyValueAndAwait(MemberResponse.from(result))
    }

    suspend fun createCouple(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val memberHeader = request.extractMemberCodeHeader()

        val command = request.awaitBodyOrNull<CoupleCreateRequest>()?.toCommand(memberHeader.no)
                ?: throw IllegalArgumentException()

        coupleCommandService.createCouple(command)

        ServerResponse.ok().buildAndAwait()
    }

    suspend fun createCoupleInfo(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val memberHeader = request.extractMemberCodeHeader()

        val command = request.awaitBodyOrNull<CoupleInfoCreateRequest>()?.toCommand(memberHeader.no)
                ?: throw IllegalArgumentException()

        coupleCommandService.createCoupleInfo(command)

        ServerResponse.ok().buildAndAwait()
    }

    suspend fun modifyCoupleInfo(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val memberHeader = request.extractMemberCodeHeader()

        val command = request.awaitBodyOrNull<CoupleInfoModifyRequest>()?.toCommand()
                ?: throw IllegalArgumentException()

        coupleCommandService.modifyCoupleInfo(memberHeader.no, command)

        ServerResponse.ok().buildAndAwait()
    }

    suspend fun createInviteCode(request: ServerRequest): ServerResponse = withContext(Dispatchers.IO) {
        val memberHeader = request.extractMemberCodeHeader()

        val code = coupleCommandService.createInviteCode(memberHeader.no)

        ServerResponse.ok().bodyValueAndAwait(code)
    }
}
