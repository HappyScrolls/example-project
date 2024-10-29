package com.yedongsoon.example_project.application.member

import com.yedongsoon.example_project.application.exception.MemberNotFoundException
import com.yedongsoon.example_project.domain.member.Member
import com.yedongsoon.example_project.domain.member.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemberQueryService(
        private val memberRepository: MemberRepository,
) {
    suspend fun getMember(memberNo: Int): Member {
        return memberRepository.findByIdOrNull(memberNo) ?: throw throw MemberNotFoundException("유저를 찾을 수 없습니다")
    }
}