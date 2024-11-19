package com.yedongsoon.example_project.presentation.router

import com.yedongsoon.example_project.presentation.handler.NotificationHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class NotificationRouter(private val notificationHandler: NotificationHandler) {
    @Bean
    fun exampleRoute(): RouterFunction<ServerResponse> {
        return coRouter {
            (accept(MediaType.APPLICATION_JSON) and "/notification").nest {
                PATCH("/fcm-key", notificationHandler::refreshFcmKey)
            }
        }
    }
}
