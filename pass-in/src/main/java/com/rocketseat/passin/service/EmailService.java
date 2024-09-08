package com.rocketseat.passin.service;

import com.rocketseat.passin.domain.attendee.Attendee;
import com.rocketseat.passin.dto.attendee.AttendeeRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /*
    // Configuração do servidor SMTP
    private static final String SMTP_HOST = "smtp.gmail.com"; // Substitua pelo seu servidor SMTP
    private static final int SMTP_PORT = 587; // Porta SMTP
    private static final String SMTP_USER = "binhosounds@hotmail.com"; // Seu e-mail
    private static final String SMTP_PASSWORD = "Bibito24@"; // Sua senha de e-mail
     */

    public void sendConfirmationEmail(Attendee attendee) {

        String token = generateConfirmationToken(attendee);
        String confirmationLink = "http://localhost:8080/events/confirmar?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(String.valueOf(attendee.getEmail()));
        message.setSubject("Confirmação de Participação");
        message.setText("Olá " + attendee.getName() + ",\n\n" +
                "Obrigado por se cadastrar para o evento. Por favor, confirme sua participação clicando no link abaixo:\n" + confirmationLink + "\n\nObrigado!");

        mailSender.send(message);

    }

    private String generateConfirmationToken(Attendee attendee) {
        // Gere um token único para a confirmação (ex: UUID)
        return UUID.randomUUID().toString();
    }
}
