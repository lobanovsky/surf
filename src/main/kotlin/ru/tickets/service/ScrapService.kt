package ru.tickets.service

import org.jsoup.Jsoup
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import ru.tickets.utils.logger

@Service
class ScrapService(
    private val emailService: EmailService
) {

    @Async
    fun go() {

        //6-9
        val url = "https://gid-kid.timepad.ru/event/2536799/"
        //8-13
//        val url = "https://gid-kid.timepad.ru/event/2536849/"

        while (true) {
            try {
                logger().info("Пробуем найти кнопку \"Купить билеты\"")
                val document = Jsoup.connect(url).get()

                val buyTicketsLink = document.select("a:contains(Купить билеты)")

                if (buyTicketsLink.isNotEmpty()) {
                    logger().info("Кнопка найдена!")
                    emailService.send(
                        subject = "Билеты доступны на 6-9 лет",
                        body = "Купить билет по ссылке! $url"
                    )
                    return
                }
                val min = minGenerator()
                val mills = min * 60_000
                logger().info("Кнопка не найдена, попробуем еще раз через $min минут ($mills миллисекунд)")

                //minutes to mills
                Thread.sleep(mills)
            } catch (e: Exception) {
                logger().error(e.message, e)
            }
        }
    }

    //generator mills from 1 to 5 mins
    fun minGenerator() = (1..5).random().toLong()
}