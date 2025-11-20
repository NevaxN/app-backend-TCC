package com.app.src.repositories;

import com.app.src.models.UsuarioPreferencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioPreferenciasRepository extends JpaRepository<UsuarioPreferencias, Integer> {

    @Query("""
        SELECT up
        FROM UsuarioPreferencias up
        JOIN up.usuario u
        JOIN Pesquisador p ON p.usuario = u
        WHERE p.id = :idPesquisador
    """)
    Optional<UsuarioPreferencias> findPreferenciasByPesquisadorId(Integer idPesquisador);

}
