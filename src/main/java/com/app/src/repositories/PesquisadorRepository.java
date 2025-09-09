package com.app.src.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.src.models.Pesquisador;

@Repository
public interface PesquisadorRepository extends JpaRepository<Pesquisador, Integer> {

    Optional<Pesquisador> findById(Integer id);

    List<Pesquisador> findAll();

    <S extends Pesquisador> S save(S pesquisador);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
