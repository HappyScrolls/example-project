package com.yedongsoon.example_project.domain.example

import org.springframework.data.jpa.repository.JpaRepository

interface ErrLogRepository : JpaRepository<ErrLog, Int> {
    fun findByNo(no: Int): ErrLog?
}