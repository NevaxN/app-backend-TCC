package com.app.src.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.src.models.Favorito;

public interface FavoritoRepository extends JpaRepository<Favorito, Integer>{
    
    Optional<Favorito> findById(Integer id);

    @Query("SELECT f.pesquisador.id FROM Favorito f WHERE f.usuario.id = :usuarioId")
    Set<Integer> findPesquisadorIdsByUsuarioId(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT f FROM Favorito f " +
           "LEFT JOIN FETCH f.usuario uFavorito " +
           "LEFT JOIN FETCH f.pesquisador pFavorito " +
           "LEFT JOIN FETCH pFavorito.usuario uPesquisador " +
           "LEFT JOIN FETCH uPesquisador.roles " +
           "LEFT JOIN FETCH uPesquisador.tipoUsuario " +
           "WHERE uFavorito.id = :usuarioId")
    List<Favorito> findByUsuarioId(@Param("usuarioId") Integer usuarioId);

    boolean existsByUsuarioIdAndPesquisadorId(Integer usuarioId, Integer pesquisadorId);

    long deleteByUsuarioIdAndPesquisadorId(Integer usuarioId, Integer pesquisadorId);

    List<Favorito> findAll();

    <S extends Favorito> S save(S favorito);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
