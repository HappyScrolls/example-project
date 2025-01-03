package com.yedongsoon.example_project.presentation.extension

import com.yedongsoon.example_project.application.exception.ExampleBadRequestException
import com.yedongsoon.example_project.application.exception.InvalidArgumentException
import com.yedongsoon.example_project.domain.extension.decodeBase64ToDto
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.queryParamOrNull
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun ServerRequest.extractMemberCodeHeader(): MemberHeader {
    return headers().header("Member-Code").firstOrNull()
            ?.let {
                it.decodeBase64ToDto<MemberHeader>()
            } ?: throw InvalidArgumentException("Invalid or missing Member-Code header variable")
}

fun ServerRequest.extractServiceCodeHeader(): ServiceHeader {
    return headers().header("Service-Code").firstOrNull()
            ?.let {
                it.decodeBase64ToDto<ServiceHeader>()
            } ?: throw InvalidArgumentException("Invalid or missing Service-Code header variable")
}

fun ServerRequest.intQueryParam(parameter: String): Int {
    return queryParamOrNull(parameter)?.toIntOrNull()
            ?: throw InvalidArgumentException("Invalid or missing '$parameter' path variable")
}


fun ServerRequest.localDateQueryParam(parameter: String): LocalDate {
    return queryParamOrNull(parameter)?.let {
        LocalDate.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    } ?: throw InvalidArgumentException()
}

fun ServerRequest.extractRawMemberCodeHeader(): String {
    return headers().header("Member-Code").firstOrNull() ?: throw ExampleBadRequestException("헤더 없음")
}

// Path Variable
fun ServerRequest.intPathVariable(variable: String): Int {
    return pathVariable(variable).toIntOrNull()
            ?: throw InvalidArgumentException("Invalid or missing '$variable' path variable")
}

fun ServerRequest.localDatePathVariable(variable: String): LocalDate {
    return pathVariable(variable).let {
        LocalDate.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    } ?: throw InvalidArgumentException("Invalid or missing '$variable' path variable")
}
