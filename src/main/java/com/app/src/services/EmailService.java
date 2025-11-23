package com.app.src.services;

import com.app.src.dto.EmpresaDTO;
import com.app.src.dto.EnviarContatoDTO;
import com.app.src.dto.PesquisadorDTO;
import com.app.src.exceptions.TempoEsperaAtivoException;
import com.app.src.models.Empresa;
import com.app.src.models.Pesquisador;
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

    private final String TITULO_CONTATO= "[LaVerse] Você recebeu uma nova mensagem";

    private final String TEMPLATE_CONTATO_EMPRESA = "contatoEmpresa.html";
    private final String TEMPLATE_CONTATO_PESQUISADOR = "contatoPesquisador.html";

    @Value("${spring.mail.username}")
    private String remetente;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private PesquisadorService pesquisadorService;

    @Autowired
    private EmpresaService empresaService;


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

    private void salvarContato (String key) {
        redisTemplate.opsForValue().set(key, "",7, TimeUnit.DAYS);
    }

    private boolean verificarContatoExistente (String key) {
        return redisTemplate.hasKey(key);
    }

    public String enviarContato (EnviarContatoDTO dados) {

        PesquisadorDTO destinatario = pesquisadorService.buscarPorId(dados.idDestinatario());
        String emailDestinatario = destinatario.getUsuario().getLogin();

        return switch (dados.tipoRemetente()) {
            case "pesquisador" -> {
                Pesquisador remetente = pesquisadorService.buscarPorIdUsuario(dados.idRemetente());
                String chaveContato = ("contato:pesquisador:" + remetente.getId() + ":" + destinatario.getId());
                if (verificarContatoExistente(chaveContato)) throw new TempoEsperaAtivoException("Falha ao enviar e-mail. Prazo de espera não respeitado.");
                String linkRemetente = "http://localhost:3000/pesquisadores/" + dados.idRemetente();
                enviarEmailContatoPesquisadorImpl(emailDestinatario, linkRemetente, TITULO_CONTATO, TEMPLATE_CONTATO_PESQUISADOR, remetente, dados.texto());
                salvarContato(chaveContato);
                yield "E-mail enviado com sucesso.";
            }
            case "empresa" -> {
                Empresa remetente = empresaService.buscarPorUsuarioId(dados.idRemetente());
                String chaveContato = ("contato:empresa:" + remetente.getId() + ":" + destinatario.getId());
                if (verificarContatoExistente(chaveContato)) throw new TempoEsperaAtivoException("Falha ao enviar e-mail. Prazo de espera não respeitado.");
                String linkRemetente = "http://localhost:3000/perfilEmpresa/" + dados.idRemetente();
                enviarEmailContatoEmpresaImpl(emailDestinatario, linkRemetente, TITULO_CONTATO, TEMPLATE_CONTATO_EMPRESA, remetente, dados.texto());
                salvarContato(chaveContato);
                yield "E-mail enviado com sucesso.";
            }
            default -> "Falha ao enviar e-mail. Tipo de usuário inválido";
        };

    }

    @Async
    public void enviarEmailContatoEmpresaImpl(String destinatario, String link, String titulo, String htmlTemplate, Empresa empresa, String texto) {

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            String htmlString;
            try (InputStream inputStream = new ClassPathResource("templates/email/" + htmlTemplate).getInputStream()) {
                htmlString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }

            String mensagemBox = "";
            if (texto != null && !texto.isBlank()) {
                mensagemBox =
                        "<div class=\"message-box\">" +
                                "<h4>Mensagem da Empresa</h4>" +
                                "<p>" + texto + "</p>" +
                                "</div>";
            }

            String empresaLink = "<a href=\"" + link + "\" style=\"color:#990000; text-decoration: underline;\">empresa</a>";

            String htmlFinal = htmlString
                    .replace("{{EMPRESA_LINK}}", empresaLink)
                    .replace("{{NOME_EMPRESA}}", empresa.getNomeComercial())
                    .replace("{{TELEFONE_CONTATO}}", empresa.getTelefone())
                    .replace("{{EMAIL_EMPRESA}}", empresa.getEmail())
                    .replace("{{MENSAGEM_BOX}}", mensagemBox);

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

    @Async
    public void enviarEmailContatoPesquisadorImpl(String destinatario, String link, String titulo, String htmlTemplate, Pesquisador pesquisador, String texto) {

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            String htmlString;
            try (InputStream inputStream = new ClassPathResource("templates/email/" + htmlTemplate).getInputStream()) {
                htmlString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }

            String ocupacaoBox = "";
            if (pesquisador.getOcupacao() != null && !pesquisador.getOcupacao().isBlank()) {
                ocupacaoBox =
                        "<div class=\"info-row\">" +
                                "<strong>Especialidade:</strong> " + pesquisador.getOcupacao() +
                                "</div>";
            }

            String mensagemBox = "";
            if (texto != null && !texto.isBlank()) {
                mensagemBox =
                        "<div class=\"message-box\">" +
                                "<h4>Mensagem do Pesquisador</h4>" +
                                "<p>" + texto + "</p>" +
                                "</div>";
            }

            String pesquisadorLink = "<a href=\"" + link + "\" style=\"color:#990000; text-decoration: underline;\">pesquisador</a>";


            String htmlFinal = htmlString
                    .replace("{{PESQUISADOR_LINK}}", pesquisadorLink)
                    .replace("{{NOME_PESQUISADOR}}", (pesquisador.getNomePesquisador() + " " + pesquisador.getSobrenome()))
                    .replace("{{OCUPACAO_BOX}}", ocupacaoBox)
                    .replace("{{EMAIL_PESQUISADOR}}", pesquisador.getUsuario().getLogin())
                    .replace("{{MENSAGEM_BOX}}", mensagemBox);

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
