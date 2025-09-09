package com.app.src.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.models.FormacaoAcademica;

public interface FormacaoAcademicaRepository extends JpaRepository<FormacaoAcademica, Integer>{
    
    Optional<FormacaoAcademica> findGraduacaoById(Integer id);

    List<FormacaoAcademica> findAll();

    <S extends FormacaoAcademica> S save(S formacao);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
