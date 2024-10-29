package com.yedongsoon.example_project.application.exception

import org.springframework.http.HttpStatus

class MemberAdditionalInfoExistsException(message: String) : AbstractException(HttpStatus.BAD_REQUEST, message)
class MemberNotFoundException(message: String) : AbstractException(HttpStatus.NOT_FOUND, message)

