package com.app.src.repositories;

import com.app.src.models.Artigo;
import com.app.src.models.FormacaoAcademica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtigoRepository extends JpaRepository<Artigo, Integer> {

    List<Artigo> findByPesquisadorId(Integer pesquisadorId);

}
