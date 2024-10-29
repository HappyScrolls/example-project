package com.yedongsoon.example_project.presentation.router

import com.yedongsoon.example_project.presentation.handler.MemberHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class MemberRouter(private val memberHandler: MemberHandler) {
    @Bean
    fun memberRoute(): RouterFunction<ServerResponse> {
        return coRouter {
            (accept(MediaType.APPLICATION_JSON) and "/account-service/member").nest {
                GET("", memberHandler::getMember)
                POST("/additional-info", memberHandler::createAdditional)
                PUT("", memberHandler::modifyMemberInfo)
            }
        }
    }
}
