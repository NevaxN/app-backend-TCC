package com.app.src.controllers;

import com.app.src.dto.LivroDTO;
import com.app.src.mappers.LivroMapper;
import com.app.src.models.Livro;
import com.app.src.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    // Listar todos os livros
    @GetMapping("/listarLivros")
    public List<LivroDTO> listarTodos() {
        return livroRepository.findAll().stream()
                .map(LivroMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar livro por ID
    @GetMapping("/listarLivro/{id}")
    public LivroDTO buscarPorId(@PathVariable Integer id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Livro não encontrado com id: " + id));

        return LivroMapper.toDTO(livro);
    }

    // Criar novo livro
    @PostMapping("/salvarLivro")
    public LivroDTO criar(@RequestBody LivroDTO livroDTO) {
        Livro livro = LivroMapper.toEntity(livroDTO);

        Livro salvo = livroRepository.save(livro);

        return LivroMapper.toDTO(salvo);
    }

    // Atualizar livro
    @PutMapping("/alterarLivro/{id}")
    public LivroDTO atualizar(@PathVariable Integer id, @RequestBody Livro dadosAtualizados) {
        Livro existente = livroRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Livro não encontrado com id: " + id));

        existente.setSequenciaProducao(dadosAtualizados.getSequenciaProducao());
        existente.setPesquisador(dadosAtualizados.getPesquisador());
        existente.setAutores(dadosAtualizados.getAutores());
        existente.setIsbn(dadosAtualizados.getIsbn());
        existente.setEditora(dadosAtualizados.getEditora());
        existente.setAno(dadosAtualizados.getAno());
        existente.setNumeroPaginas(dadosAtualizados.getNumeroPaginas());
        existente.setDestaque(dadosAtualizados.getDestaque());
        existente.setIdioma(dadosAtualizados.getIdioma());
        existente.setTitulo(dadosAtualizados.getTitulo());

        Livro salvo = livroRepository.save(existente);

        return LivroMapper.toDTO(salvo);
    }

    // Deletar livro
    @DeleteMapping("/excluirLivro/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!livroRepository.existsById(id)) {
            throw new NoSuchElementException("Livro não encontrado com id: " + id);
        }
        livroRepository.deleteById(id);
    }
}
