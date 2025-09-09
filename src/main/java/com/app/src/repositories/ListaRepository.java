package com.app.src.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.models.Lista;

public interface ListaRepository extends JpaRepository<Lista, Integer> {
        
    Optional<Lista> findById(Integer id);

    List<Lista> findAll();

    <S extends Lista> S save(S lista);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
