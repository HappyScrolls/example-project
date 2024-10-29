package com.yedongsoon.example_project.domain.couple

import org.springframework.data.jpa.repository.JpaRepository

interface CoupleRepository : JpaRepository<Couple, Int> {
    fun findByAccountNoAOrAccountNoB(accountNoA: Int, accountNoB: Int): Couple?
}