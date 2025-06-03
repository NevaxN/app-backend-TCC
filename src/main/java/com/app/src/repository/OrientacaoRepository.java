package com.app.src.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.model.Endereco;
import com.app.src.model.Orientacao;

public interface OrientacaoRepository extends JpaRepository<Orientacao, Integer>{
    
    Optional<Orientacao> findById(Integer id);

    List<Orientacao> findAll();

    <S extends Endereco> S save(S endereco);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
