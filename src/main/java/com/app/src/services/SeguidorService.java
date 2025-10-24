package com.app.src.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.src.dto.SeguidorDTO;
import com.app.src.mappers.SeguidorMapper;
import com.app.src.models.Seguidor;
import com.app.src.repositories.PesquisadorRepository;
import com.app.src.repositories.SeguidorRepository;
import com.app.src.repositories.UsuarioRepository;

@Service
public class SeguidorService extends GenericCrudService<Seguidor, SeguidorDTO, Integer, SeguidorRepository> {

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public SeguidorService(SeguidorRepository repository, SeguidorMapper mapper){
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "seguidores", key = "#id")
    public SeguidorDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    @Cacheable(value = "usuarios_seguidores", key = "#id")
    public List<Seguidor> buscarPorUsuarioId(Integer id){
        return repository.findByUsuarioId(id);
    }

    @Override
    @CacheEvict(value = "usuarios_seguidores", key = "#seguidorDTO.usuarioId")
    public SeguidorDTO salvar(SeguidorDTO seguidorDTO){

        Integer pesquisadorId = seguidorDTO.getPesquisador().getId();
        Integer usuarioId = seguidorDTO.getUsuario().getId();

        if (pesquisadorId == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }
        if (usuarioId == null) {
            throw new IllegalArgumentException("ID do usuário é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(pesquisadorId)) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + pesquisadorId);
        }

        if (!usuarioRepository.existsById(usuarioId)) {
            throw new NoSuchElementException("Usuário não encontrado com id: " + usuarioId);
        }

        if (repository.existsByUsuarioIdAndPesquisadorId(usuarioId, pesquisadorId)) {
            throw new IllegalStateException("Usuário já segue este pesquisador.");
        }

        return super.salvar(seguidorDTO);
    }

    @Transactional
    @CacheEvict(value = "usuarios_seguidores", key = "#usuarioId")
    public void deixarDeSeguir(Integer usuarioId, Integer pesquisadorId) {
        if (pesquisadorId == null || usuarioId == null) {
            throw new IllegalArgumentException("IDs do usuário e do pesquisador são obrigatórios.");
        }
        
        long linhasDeletadas = repository.deleteByUsuarioIdAndPesquisadorId(usuarioId, pesquisadorId);

        if (linhasDeletadas == 0) {
            throw new NoSuchElementException("Relação 'seguir' não encontrada para este usuário e pesquisador.");
        }
    }
}
