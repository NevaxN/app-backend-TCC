package com.app.src.repositories;

import java.util.Optional;

import com.app.src.enums.TipoUsuarioName;
import com.app.src.models.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Integer> {
    Optional<TipoUsuario> findByName(TipoUsuarioName name);
}
