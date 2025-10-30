package com.app.src.repositories;

import com.app.src.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findById(Integer id);

    @Query("SELECT t FROM Tag t LEFT JOIN FETCH t.listaTags WHERE t.pesquisador.id = :id")
    List<Tag> findListaByPesquisadorId(@Param("id") Integer id); 

    List<Tag> findAll();

    <S extends Tag> S save(S tag);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    Tag findByPesquisadorId(Integer pesquisadorId);

    // Novo método para busca por tag
    @Query("SELECT t FROM Tag t JOIN FETCH t.pesquisador JOIN t.listaTags tagString WHERE tagString = :termo")
    List<Tag> findByTagContaining(@Param("termo") String termo);
}