package com.yedongsoon.example_project.presentation.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.ResourceHandlerRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
class OpenApiConfig : WebFluxConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/docs/**")
                .addResourceLocations("classpath:/static/docs/")  // JAR 내부의 리소스 경로로 수정
    }
}