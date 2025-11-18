package com.app.src.repositories;

import com.app.src.models.Empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    Optional<Empresa> findById(Integer id);

    Optional<Empresa> findByUsuarioLogin(String login);

    Optional<Empresa> findByUsuarioId(Integer usuarioId);

    List<Empresa> findAll();

    <S extends Empresa> S save(S empresa);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    // Novo método para busca por termo
    @Query("SELECT e FROM Empresa e WHERE " +
           "LOWER(e.nomeComercial) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(e.nomeRegistro) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(e.setor) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(e.frase) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(e.textoEmpresa) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Empresa> findByTermo(@Param("termo") String termo);
}