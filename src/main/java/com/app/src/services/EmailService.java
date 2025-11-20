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

    private final String TITULO_VERIFICAR_EMAIL = "[LaVerse] Verificação de e-mail";
    private final String TEMPLATE_VERIFICAR_EMAIL = "verificacaoEmail.html";

    private final String TITULO_REDEFINIR_SENHA = "[LaVerse] Redefinição de senha";
    private final String TEMPLATE_REDEFINIR_SENHA = "redefinicaoSenha.html";

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.mail.username}")
    private String remetente;
    @Autowired
    private UsuarioService usuarioService;

    public String enviarVerificacaoDeEmail(String destinatario) {
        String codigoUnico = SecureRandomGenerator.generateToken();
        redisTemplate.opsForValue().set("verificacaoEmail:" + codigoUnico, destinatario, 1, TimeUnit.HOURS);

        String linkUnico = "http://localhost:3000/verificarEmail?codigo=" + codigoUnico;

        try {
            enviarEmail(destinatario, linkUnico, TITULO_VERIFICAR_EMAIL, TEMPLATE_VERIFICAR_EMAIL);
            return "E-mail enviado com sucesso.";
        } catch (Exception e){
            return "Falha ao enviar e-mail";
        }
    }

    public String enviarRedefinicaoDeSenha(String destinatario) {

        if (usuarioService.findByLogin(destinatario) == null) {
            return "Usuário não encontrado.";
        }

        String codigoUnico = SecureRandomGenerator.generateToken();
        redisTemplate.opsForValue().set("redefinicaoSenha:" + codigoUnico, destinatario, 1, TimeUnit.HOURS);

        String linkUnico = "http://localhost:3000/redefinirSenha?codigo=" + codigoUnico;

        try {
            enviarEmail(destinatario, linkUnico, TITULO_REDEFINIR_SENHA, TEMPLATE_REDEFINIR_SENHA);
            return "E-mail enviado com sucesso.";
        } catch (Exception e) {
            return "Falha ao enviar e-mail";
        }

    }

    @Async
    public void enviarEmail(String destinatario, String link, String titulo, String htmlTemplate) {
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
            helper.setSubject(titulo);
            helper.setText(htmlFinal, true);

            helper.addInline("logoEmpresa", new ClassPathResource("images/laverse/logo.png"));

            emailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
