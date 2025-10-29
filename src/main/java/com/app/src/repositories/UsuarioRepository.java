package com.app.src.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.src.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

    Optional<Usuario> findById(Integer id);

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.roles LEFT JOIN FETCH u.tipoUsuario WHERE u.login = :login")
    Optional<Usuario> findByLogin(@Param("login") String login);

    List<Usuario> findAll();

    <S extends Usuario> S save(S usuario);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}