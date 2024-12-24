package com.yedongsoon.example_project.domain.couple

import com.yedongsoon.example_project.domain.member.model.CoupleInfoCreateCommand
import com.yedongsoon.example_project.domain.member.model.CoupleInfoModifyCommand
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "couple")
class Couple(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "couple_no")
        val no: Int = 0,

        @Column(name = "account_no_a")
        val accountNoA: Int,

        @Column(name = "account_no_b")
        val accountNoB: Int,

        ) {
    @Column(name = "couple_name")
    var name: String? = null
        private set

    @Column(name = "nick_name_a")
    var nickNameA: String? = null
        private set

    @Column(name = "nick_name_b")
    var nickNameB: String? = null
        private set

    @Column(name = "started_at")
    var startedAt: LocalDate? = null
        private set

    @Column(name = "couple_img")
    var coupleImg: String? = null
        private set

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()

    fun createInfo(command: CoupleInfoCreateCommand) {
        val (assignNickNameA, assignNickNameB) =
                if (command.memberNo == accountNoA) Pair(command.nickNameA, command.nickNameB)
                else Pair(command.nickNameB, command.nickNameA)

        name = command.name
        nickNameA = assignNickNameA
        nickNameB = assignNickNameB
        startedAt = command.startedAt
        coupleImg = command.coupleImg
    }

    fun modify(command: CoupleInfoModifyCommand) {
        if (command.name != null) name = command.name
        if (command.nickNameA != null) nickNameA = command.nickNameA
        if (command.nickNameB != null) nickNameB = command.nickNameB
        if (command.startedAt != null) startedAt = command.startedAt
        if (command.coupleImg != null) coupleImg = command.coupleImg
    }

    companion object {
        fun create(accountNoA: Int, accountNoB: Int) = Couple(
                accountNoA = accountNoA,
                accountNoB = accountNoB
        )
    }

}
