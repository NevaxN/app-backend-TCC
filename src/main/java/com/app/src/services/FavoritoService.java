package com.app.src.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.src.dto.FavoritoDTO;
import com.app.src.mappers.FavoritoMapper;
import com.app.src.models.Favorito;
import com.app.src.models.Pesquisador;
import com.app.src.models.Usuario;
import com.app.src.repositories.FavoritoRepository;
import com.app.src.repositories.PesquisadorRepository;

@Service
public class FavoritoService extends GenericCrudService<Favorito, FavoritoDTO, Integer, FavoritoRepository> {

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @Autowired
    FavoritoRepository favoritoRepository;

    public FavoritoService(FavoritoRepository repository, FavoritoMapper mapper){
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "favoritos", key = "#id")
    public FavoritoDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    public List<Favorito> buscarPorUsuarioId(Integer usuarioId){
        return repository.findByUsuarioId(usuarioId);
    }

    @CacheEvict(value = "idsSeguindo", key = "#usuarioLogado.id")
    public FavoritoDTO salvar(FavoritoDTO favoritoDTO, Usuario usuarioLogado){
        if (favoritoDTO.getPesquisadorId() == null) {
            throw new IllegalArgumentException("pesquisadorId não pode ser nulo");
        }

        Pesquisador pesquisadorASerSeguido = pesquisadorRepository.findById(favoritoDTO.getPesquisadorId())
        .orElseThrow(() -> new NoSuchElementException("Pesquisador não encontrado"));

        Favorito novoFavorito = new Favorito();
        novoFavorito.setUsuario(usuarioLogado);
        novoFavorito.setPesquisador(pesquisadorASerSeguido);

        Favorito favoritoSalvo = repository.save(novoFavorito);

        return mapper.toDTO(favoritoSalvo);
    }

    @Transactional
    @CacheEvict(value = "usuarios_favoritos", key = "#usuarioId")
    public void deixarDeSeguir(Integer usuarioId, Integer pesquisadorId) {
        if (pesquisadorId == null || usuarioId == null) {
            throw new IllegalArgumentException("IDs do usuário e do pesquisador são obrigatórios.");
        }
        
        long linhasDeletadas = repository.deleteByUsuarioIdAndPesquisadorId(usuarioId, pesquisadorId);

        if (linhasDeletadas == 0) {
            throw new NoSuchElementException("Relação 'favorito' não encontrada para este usuário e pesquisador.");
        }
    }

    @Cacheable(value = "idsFavorito", key = "#usuarioId") // <-- Cacheie ISSO!
    public Set<Integer> buscarIdsPesquisadoresPorUsuarioId(Integer usuarioId) {
        return favoritoRepository.findPesquisadorIdsByUsuarioId(usuarioId);
    }
}
