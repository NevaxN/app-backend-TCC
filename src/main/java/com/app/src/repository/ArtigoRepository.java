package com.app.src.repository;

import com.app.src.model.Artigo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtigoRepository extends JpaRepository<Artigo, Integer> {
}
