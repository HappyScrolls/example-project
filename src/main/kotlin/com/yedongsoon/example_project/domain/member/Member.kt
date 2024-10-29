package com.yedongsoon.example_project.domain.member

import com.yedongsoon.example_project.domain.member.model.MemberAdditionalInfoCommand
import com.yedongsoon.example_project.domain.member.model.MemberInfoModifyCommand
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "member")
class Member(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "member_no")
        val no: Int = 0,

        @Column(name = "account_id")
        val accountId: String?,

        name: String?,

        email: String?,

        profilePhoto: String?,

        birthDate: LocalDate?,

        mobileNo: String?,
) {
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "name")
    var name: String? = name
        private set

    @Column(name = "email")
    var email: String? = email
        private set

    @Column(name = "profile_photo")
    var profilePhoto: String? = profilePhoto
        private set

    @Column(name = "birth_date")
    var birthDate: LocalDate? = birthDate
        private set

    @Column(name = "mobile_no")
    var mobileNo: String? = mobileNo
        private set

    fun createAdditionalInfo(command: MemberAdditionalInfoCommand) {
        name = command.name ?: name
        profilePhoto = command.profilePhoto
        birthDate = command.birthDate
        mobileNo = command.mobileNo
    }

    fun modify(command: MemberInfoModifyCommand) {
        name = command.name
        email = command.email
        profilePhoto = command.profilePhoto
        birthDate = command.birthDate
        mobileNo = command.mobileNo
    }
}