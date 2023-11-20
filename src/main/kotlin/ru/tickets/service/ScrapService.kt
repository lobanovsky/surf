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
    fun go(email: String) {
        //6-9
        val url = "https://gid-kid.timepad.ru/event/2536799/"
        //8-13
//        val url = "https://gid-kid.timepad.ru/event/2536849/"
        while (true) {
            logger().info("Пробуем найти кнопку \"Купить билеты\"")
            val doc = Jsoup.connect(url).get()

            val buyButtons =
                doc.select("a.abtn.abtn--purple.abtn--block.abtn--king.abtn--center:containsOwn(Купить билеты)")

            for (buyButton in buyButtons) {
                logger().info("Кнопка найдена!")
                emailService.send(
                    email,
                    "Билеты доступны на 6-9 лет",
                    "Купить билет по ссылке! https://gid-kid.timepad.ru/event/2536849/"
                )
                return
            }
            val min = minGenerator()
            val mills = min * 60_000
            logger().info("Кнопка не найдена, попробуем еще раз через $min минут ($mills миллисекунд)")

            //minutes to mills
            Thread.sleep(mills)
        }
    }

    //generator mills from 60_000 to 350_000
    fun minGenerator() = (1..5).random().toLong()
}