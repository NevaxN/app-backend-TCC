package com.app.src.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.models.ProjetoPesquisa;

public interface ProjetoPesquisaRepository extends JpaRepository<ProjetoPesquisa, Integer>{
    Optional<ProjetoPesquisa> findById(Integer id);

    List<ProjetoPesquisa> findAll();

    <S extends ProjetoPesquisa> S save(S projetoPesquisa);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    List<ProjetoPesquisa> findByPesquisadorId(Integer pesquisadorId);
}
