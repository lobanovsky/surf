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

    @Async
    fun goKids() {
        val url = "https://mftickets.technolab.com.ru/mc/66a8ed7504b149e0b190687d"
        val options = ChromeOptions()
        options.addArguments("--headless")
        val driver: WebDriver = ChromeDriver(options)
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
                                emailService.send("Билеты доступны на 6-9 лет", "Купить билет по ссылке! $url")
//                                return
                            }
                        }
                    }
                    waiting("6-9 лет")
                } catch (e: Exception) {
                    logger().error(e.message, e)
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
        val options = ChromeOptions()
        options.addArguments("--headless")
        val driver: WebDriver = ChromeDriver(options)
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
                                emailService.send("Билеты 18+", "Купить билет по ссылке! $url")
//                                return
                            }
                        }
                    }
                    waiting("18+")
                } catch (e: Exception) {
                    logger().error(e.message, e)
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
        val second = (1..3).random().toLong()
        val mills = second * 1000
        logger().info("$prefix мест нет, попробуем еще раз через $second секунд ($mills миллисекунд)")
        //minutes to mills
        Thread.sleep(mills)
    }

}