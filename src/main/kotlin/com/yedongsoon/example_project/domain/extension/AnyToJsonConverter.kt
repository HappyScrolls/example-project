package com.yedongsoon.example_project.domain.extension

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class AnyToJsonConverter : AttributeConverter<Any?, String?> {


    override fun convertToDatabaseColumn(attribute: Any?): String? {
        return attribute?.toJson()
    }

    override fun convertToEntityAttribute(dbData: String?): Any? {
        return dbData
    }
}
