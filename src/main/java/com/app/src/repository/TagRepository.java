package com.app.src.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer>{
    
    Optional<Tag> findById(Integer id);

    List<Tag> findAll();

    <S extends Tag> S save(S tag);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
