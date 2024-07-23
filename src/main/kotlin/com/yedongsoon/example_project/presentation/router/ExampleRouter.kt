package com.yedongsoon.example_project.presentation.router

import com.yedongsoon.example_project.presentation.handler.ExampleHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ExampleRouter(private val exampleHandler: ExampleHandler) {
    @Bean
    fun exampleRoute(): RouterFunction<ServerResponse> {
        return coRouter {
            (accept(MediaType.APPLICATION_JSON) and "/example").nest {
                POST("", exampleHandler::createExample)
//                GET("{id}", exampleHandler::getExample)
            }
        }
    }
}
