package ru.surf.service

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import ru.surf.utils.logger
import java.time.Duration

@Service
class SurfService(
    private val emailService: EmailService
) {

    private fun initDriver(): WebDriver {
        val options = ChromeOptions()
        options.addArguments("--headless")
        val driver: WebDriver = ChromeDriver(options)
        return driver
    }

    @Async
    fun goKids() {
        val url = "https://mftickets.technolab.com.ru/mc/66a8ed7504b149e0b190687d"
        var driver = initDriver()
        try {
            while (true) {
                try {
                    driver.get(url)
                    // Ожидаем, пока элемент, содержащий текст "Осталось мест:", станет видимым
                    val wait = WebDriverWait(driver, Duration.ofSeconds(10))
                    // Ищем все элементы, содержащие текст "Осталось мест:"
                    val elements: List<WebElement> = wait.until(
                        ExpectedConditions.visibilityOfAllElementsLocatedBy(
                            By.xpath("//*[contains(text(), 'Осталось мест:')]")
                        )
                    )

                    val slots: List<WebElement> = driver.findElements(By.cssSelector(".Place_CardContent__7AO5e a"))

                    for (slot in slots) {
                        val slotInfo = slot.findElement(By.cssSelector(".TimeSlot_CountText__c7mqr")).text
                        val time = slot.findElement(By.cssSelector(".TimeSlot_Time___zyty")).text
                        val description = slot.findElement(By.cssSelector(".TimeSlot_Desc__tX0zr")).text

                        val split = slotInfo.split(" ")
                        var freePlaces = 0
                        if (split.size > 2) {
                            freePlaces = split[2].toInt()
                        }

                        // Проверяем, есть ли свободные места
//                        if (freePlaces > 0 && time.contains("17:00")) {
                        if (freePlaces > 0) {
                            emailService.send("Билеты 6-9 на $time", "Купить билет по ссылке! $url")
                        } else {
                            logger().info("Билеты 6-9 на $time мест нет")
                        }
                    }
                    waiting("6-9 лет")
                } catch (e: Exception) {
                    logger().error(e.message, e)
                    driver.quit()
                    driver = initDriver()
                }
            }
        } catch (e: Exception) {
            logger().error("Error: ${e.message}", e)
        } finally {
            driver.quit()
        }
    }

    @Async
    fun goAdult() {
        val url = "https://mftickets.technolab.com.ru/mc/66a8ec3a0e7b49001f7c8277"
        var driver = initDriver()
        try {
            while (true) {
                try {
                    driver.get(url)
                    // Ожидаем, пока элемент, содержащий текст "Осталось мест:", станет видимым
                    val wait = WebDriverWait(driver, Duration.ofSeconds(10))
                    // Ищем все элементы, содержащие текст "Осталось мест:"
                    val elements: List<WebElement> = wait.until(
                        ExpectedConditions.visibilityOfAllElementsLocatedBy(
                            By.xpath("//*[contains(text(), 'Осталось мест:')]")
                        )
                    )
                    val slots: List<WebElement> = driver.findElements(By.cssSelector(".Place_CardContent__7AO5e a"))

                    for (slot in slots) {
                        val slotInfo = slot.findElement(By.cssSelector(".TimeSlot_CountText__c7mqr")).text
                        val time = slot.findElement(By.cssSelector(".TimeSlot_Time___zyty")).text
                        val description = slot.findElement(By.cssSelector(".TimeSlot_Desc__tX0zr")).text

                        val split = slotInfo.split(" ")
                        var freePlaces = 0
                        if (split.size > 2) {
                            freePlaces = split[2].toInt()
                        }

                        // Проверяем, есть ли свободные места
//                        if (freePlaces > 0 && time.contains("15:00")) {
                        if (freePlaces > 0) {
                            emailService.send("Билеты 18+ на $time", "Купить билет по ссылке! $url")
                        } else {
                            logger().info("Билеты 18+ на $time мест нет")
                        }
                    }

                    waiting("18+")
                } catch (e: Exception) {
                    logger().error(e.message, e)
                    driver.quit()
                    driver = initDriver()
                }
            }
        } catch (e: Exception) {
            logger().error("Error: ${e.message}", e)
        } finally {
            driver.quit()
        }
    }


    @Async
    fun goAero() {
        val url = "https://mftickets.technolab.com.ru/mc/66a8ee6604b149e0b19069cf"
        var driver = initDriver()
        try {
            while (true) {
                try {
                    driver.get(url)
                    // Ожидаем, пока элемент, содержащий текст "Осталось мест:", станет видимым
                    val wait = WebDriverWait(driver, Duration.ofSeconds(10))
                    // Ищем все элементы, содержащие текст "Осталось мест:"
                    val elements: List<WebElement> = wait.until(
                        ExpectedConditions.visibilityOfAllElementsLocatedBy(
                            By.xpath("//*[contains(text(), 'Осталось мест:')]")
                        )
                    )
                    // Перебираем и выводим текст всех найденных элементов, если в тексте есть число больше 0, отправляем письмо
                    for (element in elements) {
                        val text = element.text
                        logger().info("Найденный текст: $text")
                        val split = text.split(" ")
                        if (split.size > 2) {
                            val count = split[2].toInt()
                            if (count > 0) {
                                emailService.send("Aero", "Купить билет по ссылке! $url")
//                                return
                            }
                        }
                    }
                    waiting("Aero")
                } catch (e: Exception) {
                    logger().error(e.message, e)
                    driver.quit()
                    driver = initDriver()
                }
            }
        } catch (e: Exception) {
            logger().error("Error: ${e.message}", e)
        } finally {
            driver.quit()
        }
    }

    private fun waiting(prefix: String) {
        // Если мест нет, ждем случайное количество секунд и пытаемся снова
        val second = (3..7).random().toLong()
        val mills = second * 1000
        logger().info("$prefix мест нет, попробуем еще раз через $second секунд ($mills миллисекунд)")
        //minutes to mills
        Thread.sleep(mills)
    }

}