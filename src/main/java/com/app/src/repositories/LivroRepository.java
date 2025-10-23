package com.app.src.repositories;

import com.app.src.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Integer> {

    List<Livro> findByPesquisadorId(Integer pesquisadorId);

}
