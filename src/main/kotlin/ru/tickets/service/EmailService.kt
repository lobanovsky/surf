package ru.tickets.service

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import ru.tickets.utils.logger

@Service
class EmailService(
    private val emailSender: JavaMailSender,
) {

    fun send(
        email: String,
        subject: String,
        body: String
    ) {
        val message = emailSender.createMimeMessage()
        val messageHelper = MimeMessageHelper(message, false, "utf-8")
        messageHelper.setFrom("docduckio@yandex.ru")
        messageHelper.setTo(email)
        messageHelper.setBcc("e.lobanovsky@ya.ru")
        messageHelper.setSubject(subject)
        messageHelper.setText(body)
        message.saveChanges()

        emailSender.send(message)
        logger().info("Email for $email has been sent")
    }

}
