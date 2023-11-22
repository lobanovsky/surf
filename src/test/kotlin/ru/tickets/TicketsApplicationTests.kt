package ru.tickets

import org.jsoup.Jsoup
import org.junit.jupiter.api.Test

class TicketsApplicationTests {

    @Test
    fun checkButton() {

//        val url = "https://gid-kid.timepad.ru/event/2536799/"
        val url = "https://gid-kid.timepad.ru/event/2536849/"

        val document = Jsoup.connect(url).get()

        val buyTicketsLink = document.select("a:contains(Купить билеты)")

        assert(buyTicketsLink.isNotEmpty())
    }

}
