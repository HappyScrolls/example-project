package com.yedongsoon.example_project.domain.example

import com.yedongsoon.example_project.application.example.model.ErrLogCreateCommand
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "err_log")
class ErrLog(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "log_no")
        val no: Int = 0,

        @Column(name = "log_content")
        val content: String,
) {
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()

    companion object {
        fun create(command: ErrLogCreateCommand) = ErrLog(
                content = command.content
        )
    }
}