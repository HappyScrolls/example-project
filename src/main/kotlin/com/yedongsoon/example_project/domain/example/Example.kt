package com.yedongsoon.example_project.domain.example

import com.yedongsoon.example_project.application.example.model.ExampleCreateCommand
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "example")
class Example(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "example_no")
        val no: Int = 0,

        @Column(name = "name")
        val name: String,

        @Column(name = "age")
        val age: Int,
) {
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()

    companion object {
        fun create(command: ExampleCreateCommand) = Example(
                name = command.name,
                age = command.age,
        )
    }
}