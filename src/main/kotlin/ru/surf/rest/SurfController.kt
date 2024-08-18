package ru.surf.rest

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import ru.surf.service.EmailService
import ru.surf.service.SurfService

@Tag(name = "Surf", description = "Surf API")
@CrossOrigin
@RestController("/surfs")
class SurfController(
    private val surfService: SurfService,
    private val emailService: EmailService,
) {

    @GetMapping()
    fun goKids() {
        surfService.goKids()
    }

    @GetMapping("/adult")
    fun goAdult() {
        surfService.goAdult()
    }

    //send test email
    @GetMapping("/test")
    fun test() {
        emailService.send("TEST: Билеты доступны на 6-9 лет", "TEST: Купить билет по ссылке!")
    }

}