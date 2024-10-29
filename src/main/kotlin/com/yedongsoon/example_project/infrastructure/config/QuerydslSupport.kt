package com.yedongsoon.example_project.infrastructure.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.support.Querydsl
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import kotlin.properties.Delegates
import kotlin.reflect.KClass

@Repository
abstract class QuerydslSupport(clazz: Class<*>) : QuerydslRepositorySupport(clazz) {
    private var queryFactory: JPAQueryFactory by Delegates.notNull()

    constructor(kClass: KClass<*>) : this(kClass.java)


    override fun getQuerydsl(): Querydsl {
        return super.getQuerydsl() ?: throw IllegalStateException("Querydsl is null")
    }
}