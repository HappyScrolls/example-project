package com.yedongsoon.example_project.application.couple

import com.yedongsoon.example_project.application.exception.CoupleNotFoundException
import com.yedongsoon.example_project.application.exception.MemberNotFoundException
import com.yedongsoon.example_project.domain.couple.Couple
import com.yedongsoon.example_project.domain.couple.CoupleRepository
import com.yedongsoon.example_project.domain.member.Member
import com.yedongsoon.example_project.domain.member.MemberRepository
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service


@Service
class CoupleQueryService(
        private val coupleRepository: CoupleRepository,
        private val memberRepository: MemberRepository,
) {
    fun getDetail(accountNo: Int): Couple {
        return coupleRepository.findByAccountNoAOrAccountNoB(accountNo, accountNo)
                ?: throw ChangeSetPersister.NotFoundException()
    }

    fun getLover(accountNo: Int): Member {
        val loverNo = coupleRepository.findByAccountNoAOrAccountNoB(accountNo, accountNo).let {
            if (it?.accountNoA == accountNo) it.accountNoB else it?.accountNoA
        } ?: throw CoupleNotFoundException("커플이 존재하지 않습니다.")


        return memberRepository.findByNo(loverNo) ?: throw MemberNotFoundException("사용자가 존재하지 않습니다.")
    }
}