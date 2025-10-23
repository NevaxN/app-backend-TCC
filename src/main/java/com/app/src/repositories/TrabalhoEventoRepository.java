package com.app.src.repositories;

import com.app.src.models.TrabalhoEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrabalhoEventoRepository extends JpaRepository<TrabalhoEvento, Integer> {

    List<TrabalhoEvento> findByPesquisadorId(Integer pesquisadorId);
}
