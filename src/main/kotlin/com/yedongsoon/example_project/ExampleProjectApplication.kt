package com.yedongsoon.example_project

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class ExampleProjectApplication

fun main(args: Array<String>) {
    runApplication<ExampleProjectApplication>(*args)
}
