package com.app.src.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.models.Orientacao;

public interface OrientacaoRepository extends JpaRepository<Orientacao, Integer>{
    
    Optional<Orientacao> findById(Integer id);

    List<Orientacao> findAll();

    <S extends Orientacao> S save(S orientacao);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
