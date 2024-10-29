package com.yedongsoon.example_project.application.couple

import com.yedongsoon.example_project.application.exception.CoupleExistException
import com.yedongsoon.example_project.application.exception.CoupleNotFoundException
import com.yedongsoon.example_project.application.exception.InvalidInviteCodeException
import com.yedongsoon.example_project.domain.couple.Couple
import com.yedongsoon.example_project.domain.couple.CoupleRepository
import com.yedongsoon.example_project.domain.extension.encodeDtoToBase64
import com.yedongsoon.example_project.domain.member.model.CoupleCreateCommand
import com.yedongsoon.example_project.domain.member.model.CoupleInfoCreateCommand
import com.yedongsoon.example_project.domain.member.model.CoupleInfoModifyCommand
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.getAndAwait
import org.springframework.data.redis.core.setAndAwait
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

@Service
class CoupleCommandService(
        private val coupleRepository: CoupleRepository,
        private val redisTemplate: ReactiveRedisTemplate<String, String>
) {
    fun createCoupleInfo(command: CoupleInfoCreateCommand) {
        coupleRepository.findByAccountNoAOrAccountNoB(command.memberNo, command.memberNo)?.let {
            it.createInfo(command)
            coupleRepository.save(it)
        } ?: throw CoupleNotFoundException("커플 정보가 존재하지 않습니다")
    }

    suspend fun createCouple(command: CoupleCreateCommand) {
        val memberNo = redisTemplate.opsForValue().getAndAwait(command.inviteCode)?.toInt()
                ?: throw InvalidInviteCodeException("유효하지 않은 초대코드입니다.")
        coupleRepository.findByAccountNoAOrAccountNoB(memberNo, command.accountNoB)?.let {
            throw CoupleExistException("이미 커플이 존재하는 회원입니다.")
        }
        coupleRepository.save(Couple.create(memberNo, command.accountNoB))
    }

    fun modifyCoupleInfo(memberNo: Int, command: CoupleInfoModifyCommand) {
        coupleRepository.findByAccountNoAOrAccountNoB(memberNo, memberNo)?.let {
            it.modify(command)
            coupleRepository.save(it)
        } ?: throw CoupleExistException("이미 커플이 존재하는 회원입니다.")


    }

    suspend fun createInviteCode(memberNo: Int): String {
        val code = memberNo.toString() + LocalDateTime.now()
        redisTemplate.opsForValue().setAndAwait(code.encodeDtoToBase64(), memberNo.toString(), Duration.ofMinutes(5))
        return code.encodeDtoToBase64()
    }
}