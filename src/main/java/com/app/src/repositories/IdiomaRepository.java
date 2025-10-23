package com.app.src.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.models.Idioma;

public interface IdiomaRepository extends JpaRepository<Idioma, Integer>{
    
    Optional<Idioma> findById(Integer id);

    List<Idioma> findAll();

    <S extends Idioma> S save(S idioma);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    List<Idioma> findByPesquisadorId(Integer pesquisadorId);
}
