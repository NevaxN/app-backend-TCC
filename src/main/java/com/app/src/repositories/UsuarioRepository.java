package com.app.src.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.src.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

    Optional<Usuario> findById(Integer id);

    Optional<Usuario> findByLogin(String login);

    List<Usuario> findAll();

    <S extends Usuario> S save(S usuario);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}