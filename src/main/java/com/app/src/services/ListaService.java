package com.app.src.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.ListaDTO;
import com.app.src.mappers.ListaMapper;
import com.app.src.models.Lista;
import com.app.src.models.Usuario;
import com.app.src.repositories.ListaRepository;
import com.app.src.repositories.PesquisadorRepository;
import com.app.src.repositories.UsuarioRepository;

@Service
public class ListaService extends GenericCrudService<Lista, ListaDTO, Integer, ListaRepository> {

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public ListaService(ListaRepository repository, ListaMapper mapper) {
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "listas", key = "#id")
    public ListaDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    @Override
    public ListaDTO salvar(ListaDTO listaDTO){
        Lista lista = mapper.toEntity(listaDTO);

        if (lista.getPesquisador() == null || lista.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(lista.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + lista.getPesquisador().getId());
        }

        return super.salvar(listaDTO);
    }

    public ListaDTO atualizar(Integer id, ListaDTO dadosAtualizados){
        Lista lista = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Lista não encontrado com id: " + id));

        ((ListaMapper) mapper).updateEntityFromDto(dadosAtualizados, lista);

        Lista salvo = repository.save(lista);

        return mapper.toDTO(salvo);
    }

    public void adicionarPerfilNaLista(Integer listaId, Integer usuarioId){
        Lista lista = repository.findById(listaId)
                .orElseThrow(() -> new NoSuchElementException("Lista não encontrada com id: " + listaId));

        Usuario perfilParaAdicionar = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com o id: " + usuarioId));
        
        lista.getPerfisSalvos().add(perfilParaAdicionar);

        repository.save(lista);
    }

    public void removerPerfilNaLista(Integer listaId, Integer usuarioId){
        Lista lista = repository.findById(listaId)
                .orElseThrow(() -> new NoSuchElementException("Lista não encontrada com id: " + listaId));

        Usuario perfilParaRemover = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com o id: " + usuarioId));

        boolean removido = lista.getPerfisSalvos().remove(perfilParaRemover);

        if(!removido){
            throw new NoSuchElementException("O usuário com id " + usuarioId + " não está na lista com id " + listaId);
        }

        repository.save(lista);
    }
}
