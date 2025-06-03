package com.app.src.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, String>{
        
    Optional<Empresa> findById(Integer id);

    List<Empresa> findAll();

    <S extends Empresa> S save(S empresa);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
