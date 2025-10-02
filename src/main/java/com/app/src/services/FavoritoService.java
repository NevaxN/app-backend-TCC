package com.app.src.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.FavoritoDTO;
import com.app.src.mappers.FavoritoMapper;
import com.app.src.models.Favorito;
import com.app.src.repositories.FavoritoRepository;
import com.app.src.repositories.PesquisadorRepository;

@Service
public class FavoritoService extends GenericCrudService<Favorito, FavoritoDTO, Integer, FavoritoRepository> {

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    public FavoritoService(FavoritoRepository repository, FavoritoMapper mapper){
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "favoritos", key = "#id")
    public FavoritoDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    @Override
    public FavoritoDTO salvar(FavoritoDTO favoritoDTO){
        Favorito favorito = mapper.toEntity(favoritoDTO);

        if (favorito.getPesquisador() == null || favorito.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(favorito.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + favorito.getPesquisador().getId());
        }

        return super.salvar(favoritoDTO);
    }
}
