package com.app.src.repositories;

import com.app.src.models.Pesquisador;
import com.app.src.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findById(Integer id);

    List<Tag> findListaByPesquisadorId(Integer id); 

    List<Tag> findAll();

    <S extends Tag> S save(S tag);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    Tag findByPesquisadorId(Integer pesquisadorId);

    // Novo método para busca por tag
    @Query("SELECT t FROM Tag t JOIN t.listaTags tag WHERE LOWER(tag) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Tag> findByTagContaining(@Param("termo") String termo);

    // Achar Pesquisadores Por Nome da Tag
    @Query("SELECT DISTINCT t.pesquisador FROM Tag t JOIN t.listaTags tagString WHERE tagString = :tagName")
    List<Pesquisador> findPesquisadoresByTagName(@Param("tagName") String tagName);
}