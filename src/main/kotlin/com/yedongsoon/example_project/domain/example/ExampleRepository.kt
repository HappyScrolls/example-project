package com.yedongsoon.example_project.domain.example

import org.springframework.data.jpa.repository.JpaRepository

interface ExampleRepository : JpaRepository<Example, Int> {
    fun findByNameAndNo(name: String, no: Int): Example?
}