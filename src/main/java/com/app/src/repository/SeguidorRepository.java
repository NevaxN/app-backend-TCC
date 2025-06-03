package com.app.src.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.model.Seguidor;

public interface SeguidorRepository extends JpaRepository<Seguidor, Integer>{
        
    Optional<Seguidor> findById(Integer id);

    List<Seguidor> findAll();

    <S extends Seguidor> S save(S seguidor);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
