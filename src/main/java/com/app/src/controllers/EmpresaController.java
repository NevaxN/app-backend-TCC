package com.app.src.controllers;

import com.app.src.dto.EmpresaDTO;
import com.app.src.models.Empresa;
import com.app.src.models.Usuario;
import com.app.src.repositories.EmpresaRepository;
import com.app.src.services.EmpresaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {
        
    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private EmpresaRepository empresaRepository;

    @GetMapping("/listarEmpresas")
    public ResponseEntity<List<EmpresaDTO>> listarTodos() {
        return ResponseEntity.ok(empresaService.buscarTodos());
    }

    // Buscar endereço por ID
    @GetMapping("/listarEmpresa/{id}")
    public ResponseEntity<EmpresaDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(empresaService.buscarPorId(id));
    }

    // Criar novo endereço
    @PostMapping("/salvarEmpresa")
    public ResponseEntity<EmpresaDTO> salvar(@RequestBody EmpresaDTO empresaDTO,
                                            @AuthenticationPrincipal Usuario usuarioLogado) {

        if (usuarioLogado == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(empresaService.salvar(empresaDTO, usuarioLogado));
    }

    // Atualizar endereço
    @PutMapping("/alterarEmpresa/{id}")
    public ResponseEntity<EmpresaDTO> atualizar(@PathVariable Integer id, @RequestBody EmpresaDTO empresaAtualizada) {
        return ResponseEntity.ok(empresaService.atualizar(id, empresaAtualizada));
    }

    // Deletar endereço
    @DeleteMapping("/excluirEmpresa/{id}")
    public ResponseEntity<String> excluir(@PathVariable Integer id) {
        return ResponseEntity.ok(empresaService.excluir(id));
    }

    @PutMapping("/{id}/imagem")
    public ResponseEntity<?> alterarImagem(@PathVariable Integer id, @RequestParam("file") MultipartFile file) throws IOException {
        // Busca a empresa pelo ID usando o Repository
        Empresa empresa = empresaRepository.findById(id).
                orElseThrow(() -> new NoSuchElementException("Empresa não encontrada"));
        
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo está vazio");
        }
        try {
            byte[] imageBytes = file.getBytes();
            empresa.setImagemPerfil(imageBytes);
            empresaRepository.save(empresa); // Salva a alteração
            return ResponseEntity.ok("Imagem alterada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Não foi possível alterar a imagem");
        }
    }

    @GetMapping("/{id}/imagem")
    public ResponseEntity<byte[]> obterImagem(@PathVariable Integer id) throws IOException {

        Empresa empresa = empresaRepository.findById(id).
                orElseThrow(() -> new NoSuchElementException("Empresa não encontrada"));

        byte[] imageBytes;
        MediaType contentType = MediaType.IMAGE_PNG;

        if (empresa.getImagemPerfil() != null && empresa.getImagemPerfil().length > 0) {
            imageBytes = empresa.getImagemPerfil();
            contentType = MediaType.IMAGE_JPEG; 
        } else {
            // OBS: Certifique-se de que existe uma imagem padrão. 
            // Se quiser uma diferente para empresas, crie "default-company.jpg" em resources/images
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
