package com.app.src.services;

import com.app.src.dto.UsuarioPreferenciasDTO;
import com.app.src.mappers.GenericMapper;
import com.app.src.models.UsuarioPreferencias;
import com.app.src.repositories.UsuarioPreferenciasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UsuarioPreferenciasService {

    @Autowired
    private UsuarioPreferenciasRepository usuarioPreferenciasRepository;

    public void salvar (UsuarioPreferencias preferencias) {
        usuarioPreferenciasRepository.save(preferencias);
    }

    public UsuarioPreferenciasDTO buscarPorPesquisadorId (Integer idPesquisador) {
        UsuarioPreferencias preferencias = usuarioPreferenciasRepository.findPreferenciasByPesquisadorId(idPesquisador).orElseThrow(NoSuchElementException::new);
        return new UsuarioPreferenciasDTO(preferencias.getUsuario().getId(), false, false);
    }


}
