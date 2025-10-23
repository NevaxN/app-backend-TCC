package com.app.src.repositories;

import com.app.src.models.Capitulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CapituloRepository extends JpaRepository<Capitulo, Integer> {

    List<Capitulo> findByPesquisadorId(Integer pesquisadorId);

}
