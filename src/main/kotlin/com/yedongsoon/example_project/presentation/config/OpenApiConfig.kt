package com.yedongsoon.example_project.presentation.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun publicApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/**")
                .packagesToScan("com.yedongsoon.example_project")
                .build()
    }

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
                .info(
                        Info()
                                .title("Schedule API")
                                .version("v1.0")
                                .description("API 명세서입니다.")
                )
    }
}