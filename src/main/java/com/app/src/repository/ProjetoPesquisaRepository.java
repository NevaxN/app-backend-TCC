package com.app.src.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.model.ProjetoPesquisa;

public interface ProjetoPesquisaRepository extends JpaRepository<ProjetoPesquisa, Integer>{
    Optional<ProjetoPesquisa> findById(Integer id);

    List<ProjetoPesquisa> findAll();

    <S extends ProjetoPesquisa> S save(S projetoPesquisa);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
