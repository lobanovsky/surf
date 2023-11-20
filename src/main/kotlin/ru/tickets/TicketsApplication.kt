package ru.tickets

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class TicketsApplication

fun main(args: Array<String>) {
    runApplication<TicketsApplication>(*args)
}
