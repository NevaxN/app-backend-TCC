package com.app.src.controllers;

import com.app.src.dto.ListaDTO;
import com.app.src.mappers.ListaMapper;
import com.app.src.models.Lista;
import com.app.src.models.Usuario;
import com.app.src.repositories.ListaRepository;
import com.app.src.repositories.PesquisadorRepository;
import com.app.src.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/listas")
public class ListaController {

    @Autowired
    private ListaRepository listaRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/listarListas")
    public List<ListaDTO> listarTodos() {
        return listaRepository.findAll().stream()
                .map(ListaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar endereço por ID
    @GetMapping("/listarLista/{id}")
    public ListaDTO buscarPorId(@PathVariable Integer id) {
        Lista lista = listaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Lista não encontrado com id: " + id));
        
        return ListaMapper.toDTO(lista);
    }

    // Criar novo endereço
    @PostMapping("/salvarLista")
    public ListaDTO criar(@RequestBody ListaDTO listaDTO) {
        Lista lista = ListaMapper.toEntity(listaDTO);

        if (lista.getPesquisador() == null || lista.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(lista.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + lista.getPesquisador().getId());
        }

        Lista salvo = listaRepository.save(lista);

        return ListaMapper.toDTO(salvo); 
    }

    @PostMapping("salvarLista/{listaId}/perfil/{usuarioId}")
    public ResponseEntity<Void> adicionarPerfilNaLista(@PathVariable Integer listaId, @PathVariable Integer usuarioId){
        Lista lista = listaRepository.findById(listaId)
                .orElseThrow(() -> new NoSuchElementException("Lista não encontrada com id: " + listaId));

        Usuario perfilParaAdicionar = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com o id: " + usuarioId));

        
        lista.getPerfisSalvos().add(perfilParaAdicionar);

        listaRepository.save(lista);

        return ResponseEntity.ok().build();
    }

    // Atualizar endereço
    @PutMapping("/alterarLista/{id}")
    public ListaDTO atualizar(@PathVariable Integer id, @RequestBody Lista listaAtualizada) {
        Lista lista = listaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Lista não encontrado com id: " + id));

        lista.setNomeLista(listaAtualizada.getNomeLista());

        Lista salvo = listaRepository.save(lista);

        return ListaMapper.toDTO(salvo); 
    }

    // Deletar endereço
    @DeleteMapping("/excluirLista/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!listaRepository.existsById(id)) {
            throw new NoSuchElementException("Lista não encontrado com id: " + id);
        }
        listaRepository.deleteById(id);
    }
}
