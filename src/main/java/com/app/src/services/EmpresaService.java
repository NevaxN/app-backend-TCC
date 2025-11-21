package com.app.src.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.src.dto.EmpresaDTO;
import com.app.src.mappers.EmpresaMapper;
import com.app.src.models.Empresa;
import com.app.src.models.Usuario;
import com.app.src.repositories.EmpresaRepository;
import com.app.src.repositories.UsuarioRepository;

@Service
public class EmpresaService extends GenericCrudService<Empresa, EmpresaDTO, Integer, EmpresaRepository> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final EmpresaMapper empresaMapper;
    
    public EmpresaService(EmpresaRepository repository, EmpresaMapper mapper){
        super(repository, mapper);
        this.empresaMapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "empresa_por_usuario", key = "#id")
    public EmpresaDTO buscarPorId(Integer id){
        Optional<Empresa> empresa = repository.findByUsuarioId(id);
    
        return super.buscarPorId(empresa.get().getId());
    }

    public EmpresaDTO salvar(EmpresaDTO dto, Usuario usuarioLogado){
        Empresa novEmpresa = mapper.toEntity(dto);

        Usuario usuario = usuarioRepository.findById(usuarioLogado.getId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado no banco"));

        novEmpresa.setUsuario(usuario);
        Empresa empresaSalva = repository.save(novEmpresa);

        return mapper.toDTO(empresaSalva);
    }

    @Cacheable(value = "empresas-por-email", key = "#login", unless = "#result == null")
    public Optional<Empresa> buscarPorLogin(String login){
        return repository.findByUsuarioLogin(login);
    }

    @CachePut(value = "empresas", key = "#id")
    @CacheEvict(value = "empresa_por_usuario", allEntries = true)
    public EmpresaDTO atualizar(Integer id, EmpresaDTO dadosAtualizados){
        Empresa existente = repository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Empresa não encontrado com id: " + id));

        this.empresaMapper.updateEntityFromDto(dadosAtualizados, existente);

        Empresa salvo = repository.save(existente);
        
        return mapper.toDTO(salvo); 
    }
}
