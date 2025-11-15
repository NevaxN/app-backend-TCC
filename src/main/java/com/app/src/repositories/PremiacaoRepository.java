package com.app.src.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.models.Premiacao;

public interface PremiacaoRepository extends JpaRepository<Premiacao, Integer>{
    
    Optional<Premiacao> findById(Integer id);

    List<Premiacao> findAll();

    <S extends Premiacao> S save(S premiacao);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    List<Premiacao> findByPesquisadorId(Integer pesquisadorId);
}
