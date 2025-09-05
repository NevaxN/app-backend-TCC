package com.app.src.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.models.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer>{
    
    Optional<Tag> findById(Integer id);

    List<Tag> findAll();

    <S extends Tag> S save(S tag);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
