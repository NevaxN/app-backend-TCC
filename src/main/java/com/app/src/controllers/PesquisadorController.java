package com.app.src.controllers;
import com.app.src.dto.TagDTO;
import com.app.src.services.ArtigoService;
import com.app.src.services.TagService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/pesquisadores")
public class PesquisadorController {

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @Autowired
    private PesquisadorService pesquisadorService;

    @Autowired
    private TagService tagService;

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

    // NOVO: Endpoint para atualizar perfil
    @PutMapping("/atualizarPerfil/{id}")
    public ResponseEntity<PesquisadorDTO> atualizarPerfil(
            @PathVariable Integer id,
            @RequestBody PesquisadorDTO dadosAtualizados) {
        return ResponseEntity.ok(pesquisadorService.atualizarPerfil(id, dadosAtualizados));
    }

    // NOVO: Endpoint para buscar pesquisador por ID do usuário
    @GetMapping("/buscarPorUsuario/{usuarioId}")
    public ResponseEntity<PesquisadorDTO> buscarPorUsuarioId(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(pesquisadorService.buscarPorUsuarioId(usuarioId));
    }

    // Deletar pesquisador
    @DeleteMapping("/excluirPesquisador/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        return ResponseEntity.ok(pesquisadorService.excluir(id));
    }

    @PutMapping("/{id}/imagem")
    public ResponseEntity<?> alterarImagem(@PathVariable Integer id, @RequestParam("file") MultipartFile file) throws IOException {
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
            Resource resource = new ClassPathResource("images/default-user.jpg");
            try (InputStream inputStream = resource.getInputStream()) {
                imageBytes = StreamUtils.copyToByteArray(inputStream);
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/dadosCompletos/{id}")
    public ResponseEntity<Map<String, Object>> buscarDadosCompletos(@PathVariable Integer id) {
        try {
            PesquisadorDTO pesquisadorDTO = pesquisadorService.buscarPorId(id);
            TagDTO tagDTO = tagService.buscarPorIdPesquisador(id);

            Map<String, Object> dadosCompletos = new HashMap<>();
            dadosCompletos.put("pesquisador", pesquisadorDTO);
            dadosCompletos.put("tags", tagDTO);

            return ResponseEntity.ok(dadosCompletos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/listarPesquisadorCompleto/{id}")
    public ResponseEntity<?> buscarPorIdCompleto(@PathVariable Integer id) {
        try {
            PesquisadorDTO pesquisadorDTO = pesquisadorService.buscarPorIdComTratamento(id);

            // Buscar tags do pesquisador
            TagDTO tagDTO;
            try {
                tagDTO = tagService.buscarPorIdPesquisador(id);
            } catch (Exception e) {
                // Se não encontrar tags, cria uma vazia
                tagDTO = new TagDTO();
                tagDTO.setListaTags(java.util.Collections.emptyList());
            }

            // Criar resposta completa
            Map<String, Object> resposta = new java.util.HashMap<>();
            resposta.put("pesquisador", pesquisadorDTO);
            resposta.put("tags", tagDTO);

            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Erro ao buscar pesquisador",
                    "message", e.getMessage()
            ));
        }
    }
}