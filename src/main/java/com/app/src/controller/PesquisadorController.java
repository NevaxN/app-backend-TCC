package com.app.src.controller;

import com.app.src.model.Pesquisador;
import com.app.src.repository.PesquisadorRepository;
import com.app.src.repository.UsuarioRepository;

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
    private UsuarioRepository usuarioRepository;

    // Criar pesquisador
    @PostMapping
    public Pesquisador criar(@RequestBody Pesquisador pesquisador) {
        if (!usuarioRepository.existsById(pesquisador.getUsuario().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + pesquisador.getUsuario().getId());
        }
        return pesquisadorRepository.save(pesquisador);
    }

    // Listar todos os pesquisadores
    @GetMapping
    public List<Pesquisador> listar() {
        return pesquisadorRepository.findAll();
    }

    // Buscar pesquisador por ID
    @GetMapping("/{id}")
    public Pesquisador buscarPorId(@PathVariable Integer id) {
        return pesquisadorRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Pesquisador não encontrado"));
    }

    // Atualizar pesquisador
    @PutMapping("/{id}")
    public Pesquisador atualizar(@PathVariable Integer id, @RequestBody Pesquisador dadosAtualizados) {
        Pesquisador existente = pesquisadorRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Pesquisador não encontrado"));

        existente.setNomePesquisador(dadosAtualizados.getNomePesquisador());
        existente.setSobrenome(dadosAtualizados.getSobrenome());
        existente.setDataNascimento(dadosAtualizados.getDataNascimento());
        existente.setNomeCitacoesBibliograficas(dadosAtualizados.getNomeCitacoesBibliograficas());
        existente.setDataAtualizacao(dadosAtualizados.getDataAtualizacao());
        existente.setHoraAtualizacao(dadosAtualizados.getHoraAtualizacao());
        existente.setNacionalidade(dadosAtualizados.getNacionalidade());
        existente.setPaisNascimento(dadosAtualizados.getPaisNascimento());
        existente.setLattesId(dadosAtualizados.getLattesId());
        existente.setUsuario(dadosAtualizados.getUsuario());

        return pesquisadorRepository.save(existente);
    }

    // Deletar pesquisador
    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Integer id) {
        if (!pesquisadorRepository.existsById(id)) {
            return "Pesquisador não encontrado";
        }
        pesquisadorRepository.deleteById(id);
        return "Pesquisador deletado com sucesso";
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
}
