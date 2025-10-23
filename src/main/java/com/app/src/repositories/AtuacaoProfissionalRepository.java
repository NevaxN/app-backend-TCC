package com.app.src.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.models.AtuacaoProfissional;

public interface AtuacaoProfissionalRepository extends JpaRepository<AtuacaoProfissional, Integer>{
    
    Optional<AtuacaoProfissional> findById(Integer id);

    List<AtuacaoProfissional> findAll();

    <S extends AtuacaoProfissional> S save(S atuacaoProfissional);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    List<AtuacaoProfissional> findByPesquisadorId(Integer pesquisadorId);
}
