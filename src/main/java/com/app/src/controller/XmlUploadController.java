package com.app.src.controller;

import com.app.src.service.XmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.Charset;

@RestController
@RequestMapping("/api")
public class XmlUploadController {

    @Autowired
    private XmlService xmlService;

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

            System.out.println("Arquivo XML recebido (primeiros 100000 caracteres):");
            System.out.println(conteudo.substring(0, Math.min(conteudo.length(), 100000)));

            return ResponseEntity.ok("{\"mensagem\": \"Arquivo XML recebido com sucesso!\"}");

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("{\"mensagem\": \"Erro ao processar o arquivo: " + e.getMessage() + "\"}");
        }
    }
}
