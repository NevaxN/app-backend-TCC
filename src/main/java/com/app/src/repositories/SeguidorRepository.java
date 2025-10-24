package com.app.src.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.models.Seguidor;

public interface SeguidorRepository extends JpaRepository<Seguidor, Integer>{
        
    Optional<Seguidor> findById(Integer id);

    List<Seguidor> findByUsuarioId(Integer id);

    boolean existsByUsuarioIdAndPesquisadorId(Integer usuarioId, Integer pesquisadorId);

    long deleteByUsuarioIdAndPesquisadorId(Integer usuarioId, Integer pesquisadorId);

    List<Seguidor> findAll();

    <S extends Seguidor> S save(S seguidor);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
