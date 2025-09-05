package com.app.src.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.models.Favorito;

public interface FavoritoRepository extends JpaRepository<Favorito, Integer>{
    
    Optional<Favorito> findById(Integer id);

    List<Favorito> findAll();

    <S extends Favorito> S save(S favorito);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
