package com.app.src.repositories;

import com.app.src.models.Pesquisador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface PesquisadorRepository extends JpaRepository<Pesquisador, Integer> {

    @Query("SELECT p FROM Pesquisador p LEFT JOIN FETCH p.usuario u LEFT JOIN FETCH u.roles LEFT JOIN FETCH u.tipoUsuario WHERE p.id = :id")
    Optional<Pesquisador> findById(@Param("id") Integer id);

    List<Pesquisador> findAll();

    <S extends Pesquisador> S save(S pesquisador);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    // Novo método para busca por termo
    @Query("SELECT p FROM Pesquisador p WHERE " +
           "LOWER(p.nomePesquisador) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(p.sobrenome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(p.nomeCitacoesBibliograficas) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Pesquisador> findByTermo(@Param("termo") String termo);
}