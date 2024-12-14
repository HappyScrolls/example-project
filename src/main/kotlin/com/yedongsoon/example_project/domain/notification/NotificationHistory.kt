package com.yedongsoon.example_project.domain.notification

import com.yedongsoon.example_project.domain.extension.AnyToJsonConverter
import com.yedongsoon.example_project.domain.extension.BooleanToYNConverter
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "notification_history")
class NotificationHistory(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "notification_no")
        val notificationNo: Int = 0,

        @Column(name = "account_no")
        val accountNo: Int,

        @Column(name = "message")
        val message: String,

        @Column(name = "path")
        val path: String?,

        @Convert(converter = AnyToJsonConverter::class)
        @Column(name = "parameter")
        var parameter: Any? = null,
) {
    @Column(name = "messaged_at")
    val messagedAt: LocalDateTime = LocalDateTime.now()

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "deleted_yn")
    var isDeleted: Boolean = false
        private set

    fun read() {
        isDeleted = true
    }

    companion object {
        fun create(command: NotificationCreateCommand) = NotificationHistory(
                accountNo = command.accountNo,
                message = command.message,
                path = command.path,
                parameter = command.parameter,
        )
    }
}
