package com.app.src.controller;

import com.app.src.model.Pesquisador;
import com.app.src.repository.PesquisadorRepository;
import com.app.src.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/pesquisadores")
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
}
