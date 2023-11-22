package ru.tickets.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import ru.tickets.utils.logger

@Service
class EmailService(
    private val emailSender: JavaMailSender,

    @Value("\${app.param.mail.from}")
    private val from: String,
    @Value("\${app.param.mail.to}")
    private val to: String,
    @Value("\${app.param.mail.bcc}")
    private val bcc: String,
) {

    fun send(
        subject: String,
        body: String
    ) {
        val message = emailSender.createMimeMessage()
        val messageHelper = MimeMessageHelper(message, false, "utf-8")
        messageHelper.setFrom(from)
        messageHelper.setTo(to)
        messageHelper.setBcc(bcc)
        messageHelper.setSubject(subject)
        messageHelper.setText(body)
        message.saveChanges()

        emailSender.send(message)
        logger().info("Email for $to has been sent")
    }

}
