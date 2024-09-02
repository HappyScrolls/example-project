package com.yedongsoon.example_project.application.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import reactor.core.publisher.Mono

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AbstractException::class)
    fun handleCustomException(ex: AbstractException): Mono<ResponseEntity<String>> {
        return Mono.just(
                ResponseEntity
                        .status(ex.status)
                        .body(ex.message)
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): Mono<ResponseEntity<String>> {
        return Mono.just(
                ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("An unexpected error occurred: ${ex.message}")
        )
    }
}