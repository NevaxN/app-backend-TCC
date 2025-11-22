package com.app.src.controllers;

import com.app.src.dto.PesquisadorDTO;
import com.app.src.models.Pesquisador;
import com.app.src.repositories.PesquisadorRepository;
import com.app.src.services.PesquisadorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/pesquisadores")
public class PesquisadorController {

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @Autowired
    private PesquisadorService pesquisadorService;

    @GetMapping("/listarPesquisadores")
    public ResponseEntity<List<PesquisadorDTO>> listarTodos() {
        return ResponseEntity.ok(pesquisadorService.buscarTodos());
    }

    @GetMapping("/listarPesquisador/{id}")
    public ResponseEntity<PesquisadorDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pesquisadorService.buscarPorId(id));
    }

    @PostMapping("/salvarPesquisador")
    public ResponseEntity<PesquisadorDTO> criar(@RequestBody PesquisadorDTO pesquisadorDTO) {
        return ResponseEntity.ok(pesquisadorService.salvar(pesquisadorDTO));
    }

    @PutMapping("/alterarPesquisador/{id}")
    public ResponseEntity<PesquisadorDTO> atualizar(@PathVariable Integer id, @RequestBody PesquisadorDTO dadosAtualizados) {
        return ResponseEntity.ok(pesquisadorService.atualizar(id, dadosAtualizados));
    }

    // Deletar pesquisador
    @DeleteMapping("/excluirPesquisador/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        return ResponseEntity.ok(pesquisadorService.excluir(id));
    }

    @PutMapping("/{id}/imagem")
    public ResponseEntity<?> alterarImagem(@PathVariable Integer id, @RequestParam("file")MultipartFile file) throws IOException {
        Pesquisador pesquisador = pesquisadorRepository.findById(id).
                orElseThrow(() -> new NoSuchElementException("Pesquisador não encontrado"));
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo está vazio");
        }
        try {
            byte[] imageBytes = file.getBytes();
            pesquisador.setImagemPerfil(imageBytes);
            pesquisadorRepository.save(pesquisador);
            return ResponseEntity.ok("Imagem alterada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Não foi possível alterar a imagem");
        }

    }

    @GetMapping("/buscarPesquisadores/{nomeTag}")
    public ResponseEntity<List<Pesquisador>> buscarPesquisadorPorNomeTag(@PathVariable String nomeTag){
        return ResponseEntity.ok(pesquisadorService.buscarPesquisadoresPorTag(nomeTag));
    }

    // Obter imagem de perfil do pesquisador
    @GetMapping("/{id}/imagem")
    public ResponseEntity<byte[]> obterImagem(@PathVariable Integer id) throws IOException {

        Pesquisador pesquisador = pesquisadorRepository.findById(id).
                orElseThrow(() -> new NoSuchElementException("Pesquisador não encontrado"));

        byte[] imageBytes;
        MediaType contentType = MediaType.IMAGE_PNG;

        if (pesquisador.getImagemPerfil() != null && pesquisador.getImagemPerfil().length > 0) {
            imageBytes = pesquisador.getImagemPerfil();
            contentType = MediaType.IMAGE_JPEG; // Ou o tipo que você salvou
        } else {
            Resource resource = new ClassPathResource("images/default-user.png");
            try (InputStream inputStream = resource.getInputStream()) {
                imageBytes = StreamUtils.copyToByteArray(inputStream);
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);

    }
}
