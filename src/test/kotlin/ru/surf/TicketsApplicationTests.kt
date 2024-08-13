package ru.surf

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class TicketsApplicationTests {

    //    @Test
    fun seleniumCheck() {
        // Настройка Selenium для использования Chrome
        val options = ChromeOptions()
        options.addArguments("--headless") // Запуск в фоновом режиме
        val driver: WebDriver = ChromeDriver(options)

        // Открываем страницу
        driver.get("https://mftickets.technolab.com.ru/mc/66a8ed7504b149e0b190687d")

        // Ожидаем, пока элемент, содержащий текст "Осталось мест:", станет видимым
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))

        // Ищем все элементы, содержащие текст "Осталось мест:"
        val elements: List<WebElement> = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//*[contains(text(), 'Осталось мест:')]")
            )
        )

        // Перебираем и выводим текст всех найденных элементов
        for (element in elements) {
            val text = element.text
            println("Найденный текст: $text")
            val split = text.split(" ")
            val count = split[2].toInt()
            if (count > 0) {
                println("Нотификация, есть места")
            }
        }

        // Закрываем драйвер
        driver.quit()
    }

}
