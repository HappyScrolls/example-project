package com.yedongsoon.example_project.domain.extension

import com.yedongsoon.example_project.enums.NotificationHistoryType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter


@Converter
class NotificationHistoryTypeConverter : AttributeConverter<NotificationHistoryType, String> {

    override fun convertToDatabaseColumn(attribute: NotificationHistoryType): String {
        return attribute.name
    }

    override fun convertToEntityAttribute(dbData: String): NotificationHistoryType {
        return NotificationHistoryType.valueOf(dbData)
    }
}
