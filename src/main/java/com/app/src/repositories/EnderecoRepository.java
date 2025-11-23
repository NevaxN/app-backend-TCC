package com.app.src.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.models.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    Optional<Endereco> findById(Integer id);

    List<Endereco> findAll();

    <S extends Endereco> S save(S endereco);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    List<Endereco> findByPesquisadorId(Integer id);
    
}
