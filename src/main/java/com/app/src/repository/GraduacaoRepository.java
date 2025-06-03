package com.app.src.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.model.Graduacao;

public interface GraduacaoRepository extends JpaRepository<Graduacao, Integer>{
    
    Optional<Graduacao> findGraduacaoById(Integer id);

    List<Graduacao> findAll();

    <S extends Graduacao> S save(S graduacao);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
