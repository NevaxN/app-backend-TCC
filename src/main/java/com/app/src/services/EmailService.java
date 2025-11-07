package com.app.src.services;

import com.app.src.utils.SecureRandomGenerator;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Service
public class EmailService {

    private final String TEMPLATE_VERIFICAR_EMAIL = "verificacaoEmail.html";
    private final String TEMPLATE_ALTERAR_SENHA = "alterarSenha.html";

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.mail.username}")
    private String remetente;

    public String enviarVerificacaoDeEmail(String destinatario) {
        String codigoUnico = SecureRandomGenerator.generateToken();
        redisTemplate.opsForValue().set("verificacaoEmail:" + codigoUnico, destinatario, 1, TimeUnit.HOURS);

        String linkUnico = "http://localhost:3000/verificarEmail?codigo=" + codigoUnico;

        try {
            enviarEmail(destinatario, linkUnico, TEMPLATE_VERIFICAR_EMAIL);
            return "E-mail enviado com sucesso.";
        } catch (Exception e){
            return "Falha ao enviar e-mail";
        }
    }

    @Async
    public void enviarEmail(String destinatario, String link, String htmlTemplate) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            String htmlString;
            try (InputStream inputStream = new ClassPathResource("templates/email/" + htmlTemplate).getInputStream()) {
                htmlString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }

            String htmlFinal = htmlString.replace("{{LINK}}", link);

            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject("Verificação de e-mail - LaVerse");
            helper.setText(htmlFinal, true);

            helper.addInline("logoEmpresa", new ClassPathResource("images/laverse/logo.png"));

            emailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
