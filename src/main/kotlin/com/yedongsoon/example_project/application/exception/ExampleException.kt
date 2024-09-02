package com.yedongsoon.example_project.application.exception

import org.springframework.http.HttpStatus

class ExampleNotFoundException(message: String) : AbstractException(HttpStatus.NOT_FOUND, message)

class ExampleBadRequestException(message: String) : AbstractException(HttpStatus.BAD_REQUEST, message)
