package com.yedongsoon.example_project.presentation


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.cors.reactive.CorsWebFilter

@Configuration
class WebFluxConfig {

    @Bean
    fun corsWebFilter(): CorsWebFilter {
        val corsConfig = CorsConfiguration()
        corsConfig.allowedOriginPatterns = listOf("*") // 와일드카드 패턴을 사용하여 모든 도메인 허용
        corsConfig.allowedMethods = listOf("*") // 모든 HTTP 메서드 허용
        corsConfig.allowedHeaders = listOf("*") // 모든 헤더 허용
        corsConfig.allowCredentials = true // 자격 증명 허용

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfig) // 모든 경로에 대해 CORS 설정 적용

        return CorsWebFilter(source)
    }
}