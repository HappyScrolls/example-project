package com.yedongsoon.example_project.application.exception

import org.springframework.http.HttpStatus

abstract class AbstractException(
        val status: HttpStatus,
        override val message: String
) : RuntimeException(message)