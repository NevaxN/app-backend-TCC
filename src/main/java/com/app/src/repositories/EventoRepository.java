package com.app.src.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.models.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    
    Optional<Evento> findById(Integer id);

    List<Evento> findAll();

    <S extends Evento> S save(S evento);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
