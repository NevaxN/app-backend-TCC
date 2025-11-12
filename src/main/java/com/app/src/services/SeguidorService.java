package com.app.src.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.src.dto.SeguidorDTO;
import com.app.src.mappers.SeguidorMapper;
import com.app.src.models.Pesquisador;
import com.app.src.models.Seguidor;
import com.app.src.models.Usuario;
import com.app.src.repositories.PesquisadorRepository;
import com.app.src.repositories.SeguidorRepository;

@Service
public class SeguidorService extends GenericCrudService<Seguidor, SeguidorDTO, Integer, SeguidorRepository> {

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @Autowired
    SeguidorRepository seguidorRepository;
    
    public SeguidorService(SeguidorRepository repository, SeguidorMapper mapper){
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "seguidores", key = "#id")
    public SeguidorDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    @Cacheable(value = "seguidores_usuario", key = "#usuarioId")
    public List<Seguidor> buscarPorUsuarioId(Integer usuarioId){
        return repository.findByUsuarioId(usuarioId);
    }

    @CacheEvict(value = "usuarios_seguidores", key = "#usuarioLogado.id")
    public SeguidorDTO salvar(SeguidorDTO seguidorDTO, Usuario usuarioLogado){
        if (seguidorDTO.getPesquisadorId() == null) {
            throw new IllegalArgumentException("pesquisadorId não pode ser nulo");
        }

        Pesquisador pesquisadorASerSeguido = pesquisadorRepository.findById(seguidorDTO.getPesquisadorId())
        .orElseThrow(() -> new NoSuchElementException("Pesquisador não encontrado"));

        Seguidor novoSeguidor = new Seguidor();
        novoSeguidor.setUsuario(usuarioLogado);
        novoSeguidor.setPesquisador(pesquisadorASerSeguido);

        Seguidor seguidorSalvo = repository.save(novoSeguidor);

        return mapper.toDTO(seguidorSalvo);
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

    @Cacheable(value = "idsSeguindo", key = "#usuarioId") // <-- Cacheie ISSO!
    public Set<Integer> buscarIdsPesquisadoresPorUsuarioId(Integer usuarioId) {
        return seguidorRepository.findPesquisadorIdsByUsuarioId(usuarioId);
}
}
