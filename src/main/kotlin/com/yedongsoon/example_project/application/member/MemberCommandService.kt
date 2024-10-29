package com.yedongsoon.example_project.application.member


import com.yedongsoon.example_project.application.exception.MemberAdditionalInfoExistsException
import com.yedongsoon.example_project.application.exception.MemberNotFoundException
import com.yedongsoon.example_project.domain.member.MemberRepository
import com.yedongsoon.example_project.domain.member.model.MemberAdditionalInfoCommand
import com.yedongsoon.example_project.domain.member.model.MemberInfoModifyCommand
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class MemberCommandService(
        private val memberRepository: MemberRepository,
) {
    @Transactional
    suspend fun createAdditional(command: MemberAdditionalInfoCommand) {
        memberRepository.findByIdOrNull(command.memberNo)?.also {
            if (it.birthDate != null && it.mobileNo != null) throw MemberAdditionalInfoExistsException("추가 정보를 이미 입력한 유저입니다.")
            it.createAdditionalInfo(command)
            memberRepository.save(it) //트랜잭션 수정후 제거
        } ?: throw throw MemberNotFoundException("유저를 찾을 수 없습니다")
    }

    @Transactional
    suspend fun modifyMemberInfo(command: MemberInfoModifyCommand) {
        memberRepository.findByIdOrNull(command.memberNo)?.also {
            it.modify(command)
            memberRepository.save(it) //트랜잭션 수정후 제거
        } ?: throw throw MemberNotFoundException("유저를 찾을 수 없습니다")
    }
}