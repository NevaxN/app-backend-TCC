package com.app.src.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.src.dto.ListaDTO;
import com.app.src.mappers.ListaMapper;
import com.app.src.models.Lista;
import com.app.src.models.Pesquisador;
import com.app.src.models.Usuario;
import com.app.src.repositories.ListaRepository;
import com.app.src.repositories.PesquisadorRepository;

@Service
public class ListaService extends GenericCrudService<Lista, ListaDTO, Integer, ListaRepository> {

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    private final ListaMapper listaMapper;
    
    public ListaService(ListaRepository repository, ListaMapper mapper) {
        super(repository, mapper);
        this.listaMapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListaDTO> buscarTodos() {
        return super.buscarTodos();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "listas", key = "#id")
    public ListaDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    public ListaDTO salvar(ListaDTO listaDTO, Usuario usuarioLogado){
        
        Pesquisador pesquisador = pesquisadorRepository.findByUsuarioId(usuarioLogado.getId())
            .orElseThrow(() -> new NoSuchElementException("Pesquisador não encontrado para o usuário: " + usuarioLogado.getLogin()));

        Lista lista = new Lista();
        lista.setNomeLista(listaDTO.getNomeLista());
        lista.setPesquisador(pesquisador);
        lista.setPerfisSalvos(new java.util.HashSet<>());

        Lista listaSalva = repository.save(lista);
        return mapper.toDTO(listaSalva);
    }

    @CachePut(value = "listas", key = "#id")
    public ListaDTO atualizar(Integer id, ListaDTO dadosAtualizados){
        Lista lista = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Lista não encontrado com id: " + id));

        this.listaMapper.updateEntityFromDto(dadosAtualizados, lista);

        Lista salvo = repository.save(lista);

        return mapper.toDTO(salvo);
    }

    @Transactional
    @CacheEvict(value = "listas", key = "#listaId")
    public boolean adicionarPerfilNaLista(Integer listaId, Integer pesquisadorId){
        Lista lista = repository.findById(listaId)
                .orElseThrow(() -> new NoSuchElementException("Lista não encontrada com id: " + listaId));

        Pesquisador pesquisador = pesquisadorRepository.findById(pesquisadorId)
                .orElseThrow(() -> new NoSuchElementException("Pesquisador não encontrado com o id: " + pesquisadorId));

        Usuario perfilParaAdicionar = pesquisador.getUsuario();
        if (perfilParaAdicionar == null) {
            throw new NoSuchElementException("Usuário não encontrado para o pesquisador com id: " + pesquisadorId);
        }
        
        boolean foiAdicionado = lista.getPerfisSalvos().add(perfilParaAdicionar);

        if (foiAdicionado) {
            repository.save(lista);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @CacheEvict(value = "listas", key = "#listaId")
    public void removerPerfilNaLista(Integer listaId, Integer pesquisadorId){
        Lista lista = repository.findById(listaId)
                .orElseThrow(() -> new NoSuchElementException("Lista não encontrada com id: " + listaId));
        
        Pesquisador pesquisador = pesquisadorRepository.findById(pesquisadorId)
                .orElseThrow(() -> new NoSuchElementException("Pesquisador não encontrado com o id: " + pesquisadorId));

        Usuario perfilParaRemover = pesquisador.getUsuario();

        boolean removido = lista.getPerfisSalvos().remove(perfilParaRemover);

        if(!removido){
            throw new NoSuchElementException("O pesquisador com id " + pesquisadorId + " não está na lista com id " + listaId);
        }

        repository.save(lista);
    }
}
