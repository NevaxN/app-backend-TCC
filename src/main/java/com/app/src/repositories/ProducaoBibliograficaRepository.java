package com.app.src.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.models.ProducaoBibliografica;

public interface ProducaoBibliograficaRepository extends JpaRepository<ProducaoBibliografica, Integer>{
    Optional<ProducaoBibliografica> findById(Integer id);

    List<ProducaoBibliografica> findAll();

    <S extends ProducaoBibliografica> S save(S producaoBibliografica);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
