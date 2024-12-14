package com.yedongsoon.example_project.domain.extension

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class AnyToJsonConverter : AttributeConverter<Any?, String?> {

    private val objectMapper = jacksonObjectMapper()

    override fun convertToDatabaseColumn(attribute: Any?): String? {
        return attribute?.toJson()
    }

    override fun convertToEntityAttribute(dbData: String?): Any? {
        return dbData
    }
}
