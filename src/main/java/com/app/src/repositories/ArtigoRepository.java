package com.app.src.repositories;

import com.app.src.models.Artigo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtigoRepository extends JpaRepository<Artigo, Integer> {
}
