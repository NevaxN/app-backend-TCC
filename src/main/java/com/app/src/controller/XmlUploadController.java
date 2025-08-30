package com.app.src.controller;

import com.app.src.model.FormacaoAcademica;
import com.app.src.model.Pesquisador;
import com.app.src.model.Usuario;
import com.app.src.repository.FormacaoAcademicaRepository;
import com.app.src.repository.PesquisadorRepository;
import com.app.src.repository.UsuarioRepository;
import com.app.src.service.XmlService;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class XmlUploadController {

    @Autowired
    private XmlService xmlService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @Autowired
    private FormacaoAcademicaRepository formacaoAcademicaRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadXml(@RequestParam("xml") MultipartFile xml) {
        if (xml.isEmpty()) {
            return ResponseEntity.badRequest().body("{\"mensagem\": \"Arquivo não enviado.\"}");
        }

        if (!xml.getOriginalFilename().endsWith(".xml")) {
            return ResponseEntity.badRequest().body("{\"mensagem\": \"Formato inválido. Envie um arquivo XML.\"}");
        }

        try {
            byte[] conteudoBytes = xml.getBytes();

            String encoding = xmlService.detectEncoding(conteudoBytes);
            String conteudo = new String(conteudoBytes, Charset.forName(encoding));

            // Transforma XML para JSON
            JSONObject jsonObject = XML.toJSONObject(conteudo);
            String jsonString = jsonObject.toString();

            System.out.println("JSON convertido do XML:");
            System.out.println(jsonString.substring(0, Math.min(jsonString.length(), 100000)));

            // Envia para o serviço Flask
            RestTemplate restTemplate = new RestTemplate();
            String flaskUrl = "http://localhost:5000/analyze";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("conteudo_xml", jsonString);

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> flaskResponse = restTemplate.postForEntity(flaskUrl, requestEntity, String.class);

            String flaskJson = flaskResponse.getBody();
            Usuario usuario = usuarioRepository.findById(1).orElseThrow();
            Pesquisador pesquisador = xmlService.converterJsonParaPesquisador(flaskJson, usuario);

            Pesquisador pesquisadorSalvo = pesquisadorRepository.save(pesquisador);

            List<FormacaoAcademica> formacaoAcademicas = xmlService.converterJsonParaFormacaoAcademica(flaskJson, pesquisadorSalvo);

            formacaoAcademicaRepository.saveAll(formacaoAcademicas);

            return ResponseEntity.ok(flaskResponse.getBody());

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("{\"mensagem\": \"Erro ao processar o arquivo: " + e.getMessage() + "\"}");
        }
    }
}
