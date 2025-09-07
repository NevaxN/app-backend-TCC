package com.app.src.repository;

import com.app.src.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
}
