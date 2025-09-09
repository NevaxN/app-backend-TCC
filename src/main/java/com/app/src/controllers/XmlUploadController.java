package com.app.src.controllers;

import com.app.src.services.XmlService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            return ResponseEntity.ok(xmlService.processarXml(xml));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"mensagem\": \"Erro ao processar o arquivo: " + e.getMessage() + "\"}");
        }
    }
}
