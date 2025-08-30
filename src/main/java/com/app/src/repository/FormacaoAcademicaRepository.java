package com.app.src.repository;

import java.util.List;
import java.util.Optional;

import com.app.src.model.FormacaoAcademica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormacaoAcademicaRepository extends JpaRepository<FormacaoAcademica, Integer>{
    
    Optional<FormacaoAcademica> findGraduacaoById(Integer id);

    List<FormacaoAcademica> findAll();

    <S extends FormacaoAcademica> S save(S formacao);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
