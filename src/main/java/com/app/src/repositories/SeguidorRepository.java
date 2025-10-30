package com.app.src.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.src.models.Seguidor;

public interface SeguidorRepository extends JpaRepository<Seguidor, Integer>{
        
    Optional<Seguidor> findById(Integer id);

    @Query("SELECT s FROM Seguidor s " +
           "LEFT JOIN FETCH s.usuario uSeguidor " +
           "LEFT JOIN FETCH s.pesquisador pSeguido " +
           "LEFT JOIN FETCH pSeguido.usuario uPesquisador " +
           "LEFT JOIN FETCH uPesquisador.roles " +
           "LEFT JOIN FETCH uPesquisador.tipoUsuario " + // Melhor já adicionar este
           "WHERE uSeguidor.id = :usuarioId")
    List<Seguidor> findByUsuarioId(@Param("usuarioId") Integer usuarioId);

    boolean existsByUsuarioIdAndPesquisadorId(Integer usuarioId, Integer pesquisadorId);

    long deleteByUsuarioIdAndPesquisadorId(Integer usuarioId, Integer pesquisadorId);

    List<Seguidor> findAll();

    <S extends Seguidor> S save(S seguidor);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
