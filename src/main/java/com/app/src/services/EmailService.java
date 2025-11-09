package com.app.src.services;

import com.app.src.models.Usuario;
import com.app.src.repositories.UsuarioRepository;
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
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class EmailService {

    private final String TEMPLATE_VERIFICAR_EMAIL = "verificacaoEmail.html";
    private final String TEMPLATE_ALTERAR_SENHA = "alterarSenha.html";
    private final String TEMPLATE_RECUPERACAO_SENHA = "recuperacaoSenha.html";

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("${spring.mail.username}")
    private String remetente;

    public String enviarVerificacaoDeEmail(String destinatario) {
        try {
            String codigoUnico = SecureRandomGenerator.generateToken();
            redisTemplate.opsForValue().set("verificacaoEmail:" + codigoUnico, destinatario, 24, TimeUnit.HOURS);

            String linkUnico = "http://localhost:3000/verificarEmail?codigo=" + codigoUnico;
            enviarEmail(destinatario, linkUnico, TEMPLATE_VERIFICAR_EMAIL, "Verificação de e-mail - LaVerse");

            return "E-mail enviado com sucesso.";
        } catch (Exception e){
            return "Falha ao enviar e-mail: " + e.getMessage();
        }
    }

    public String reenviarVerificacaoDeEmail(String destinatario) {
        try {
            // Remove qualquer código anterior para este email
            Set<String> keys = redisTemplate.keys("verificacaoEmail:*");
            if (keys != null) {
                for (String key : keys) {
                    String emailSalvo = (String) redisTemplate.opsForValue().get(key);
                    if (destinatario.equals(emailSalvo)) {
                        redisTemplate.delete(key);
                    }
                }
            }

            // Gera novo código e envia email
            String codigoUnico = SecureRandomGenerator.generateToken();
            redisTemplate.opsForValue().set("verificacaoEmail:" + codigoUnico, destinatario, 24, TimeUnit.HOURS);

            String linkUnico = "http://localhost:3000/verificarEmail?codigo=" + codigoUnico;
            enviarEmail(destinatario, linkUnico, TEMPLATE_VERIFICAR_EMAIL, "Verificação de e-mail - LaVerse");

            return "E-mail reenviado com sucesso.";
        } catch (Exception e) {
            return "Falha ao reenviar e-mail: " + e.getMessage();
        }
    }

    public String alterarEmailEReenviarVerificacao(String emailAntigo, String emailNovo) {
        try {
            // Verifica se o usuário existe
            Usuario usuario = usuarioRepository.findByLogin(emailAntigo)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Verifica se o novo email já está em uso (excluindo o próprio usuário)
            if (usuarioRepository.findByLogin(emailNovo).isPresent() && !emailAntigo.equals(emailNovo)) {
                return "Este e-mail já está em uso por outro usuário.";
            }

            // Remove códigos de verificação antigos
            Set<String> keys = redisTemplate.keys("verificacaoEmail:*");
            if (keys != null) {
                for (String key : keys) {
                    String emailSalvo = (String) redisTemplate.opsForValue().get(key);
                    if (emailAntigo.equals(emailSalvo)) {
                        redisTemplate.delete(key);
                    }
                }
            }

            // Atualiza o email do usuário
            usuario.setLogin(emailNovo);
            usuario.setEmailVerificado(false); // Marca como não verificado
            usuarioRepository.save(usuario);

            // Gera novo código de verificação para o NOVO email
            String codigoUnico = SecureRandomGenerator.generateToken();
            redisTemplate.opsForValue().set("verificacaoEmail:" + codigoUnico, emailNovo, 24, TimeUnit.HOURS);

            String linkUnico = "http://localhost:3000/verificarEmail?codigo=" + codigoUnico;
            enviarEmail(emailNovo, linkUnico, TEMPLATE_VERIFICAR_EMAIL, "Verificação de e-mail - LaVerse");

            return "E-mail alterado com sucesso! Novo código de verificação enviado para " + emailNovo + ".";

        } catch (Exception e) {
            return "Erro ao alterar e-mail: " + e.getMessage();
        }
    }

    // MÉTODO: Enviar email de recuperação de senha
    @Async
    public void enviarEmailRecuperacaoSenha(String destinatario, String link) {
        try {
            enviarEmail(destinatario, link, TEMPLATE_RECUPERACAO_SENHA, "Recuperação de Senha - LaVerse");
            System.out.println("Email de recuperação enviado com sucesso para: " + destinatario);
        } catch (Exception e) {
            System.err.println("Erro ao enviar email de recuperação: " + e.getMessage());
            throw new RuntimeException("Erro ao enviar email de recuperação: " + e.getMessage());
        }
    }

    @Async
    public void enviarEmail(String destinatario, String link, String htmlTemplate, String assunto) {
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
            helper.setSubject(assunto);
            helper.setText(htmlFinal, true);

            helper.addInline("logoEmpresa", new ClassPathResource("images/laverse/logo.png"));

            emailSender.send(message);
            System.out.println("E-mail enviado com sucesso para: " + destinatario);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}