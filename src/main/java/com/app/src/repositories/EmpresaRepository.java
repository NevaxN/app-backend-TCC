package com.app.src.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.src.models.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer>{
        
    Optional<Empresa> findById(Integer id);

    List<Empresa> findAll();

    <S extends Empresa> S save(S empresa);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
