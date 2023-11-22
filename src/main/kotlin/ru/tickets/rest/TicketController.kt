package ru.tickets.rest

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import ru.tickets.service.ScrapService

@Tag(name = "Ticket", description = "Ticket API")
@CrossOrigin
@RestController("/tickets")
class TicketController(
    private val scrapService: ScrapService
) {

    @GetMapping()
    fun getUser() {
        scrapService.go()
    }

}