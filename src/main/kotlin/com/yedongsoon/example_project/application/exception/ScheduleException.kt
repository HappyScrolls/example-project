package com.yedongsoon.example_project.application.exception

import org.springframework.http.HttpStatus

class ScheduleNotFoundException(message: String) : AbstractException(HttpStatus.NOT_FOUND, message)
class ScheduleDuplicatedException(message: String) : AbstractException(HttpStatus.BAD_REQUEST, message)
