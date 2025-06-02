package com.app.src.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    Optional<Endereco> findEnderecoById(Integer id);

    List<Endereco> findAll();

    <S extends Endereco> S save(S endereco);

    void deleteById(Integer id);

    boolean existsById(Integer id);
    
}
