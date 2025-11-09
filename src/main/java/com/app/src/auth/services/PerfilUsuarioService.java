package com.app.src.auth.services;

import com.app.src.auth.models.UsuarioDetailsImpl;
import com.app.src.dto.PerfilUsuario;
import com.app.src.enums.TipoUsuarioName;
import com.app.src.services.EmpresaService;
import com.app.src.services.PesquisadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerfilUsuarioService {

    @Autowired
    private PesquisadorService pesquisadorService;

    @Autowired
    private EmpresaService empresaService;

    public PerfilUsuario buscarPerfilUsuario(UsuarioDetailsImpl usuarioDetails) {
        TipoUsuarioName tipoUsuario = usuarioDetails.getUsuario().getTipoUsuario().getName();
        String login = usuarioDetails.getUsername();

        return switch (tipoUsuario) {
            case PESQUISADOR -> buscarPerfilPesquisador(login);
            case EMPRESA -> buscarPerfilEmpresa(login);
            default -> new PerfilUsuario("indefinido", null);
        };
    }

    private PerfilUsuario buscarPerfilPesquisador(String login) {
        return pesquisadorService.buscarPorLogin(login)
                .map(p -> new PerfilUsuario("pesquisador", p.getId()))
                .orElse(new PerfilUsuario("pesquisador_pendente", null));
    }

    private PerfilUsuario buscarPerfilEmpresa(String login) {
        return empresaService.buscarPorLogin(login)
                .map(e -> new PerfilUsuario("empresa", e.getId()))
                .orElse(new PerfilUsuario("empresa_pendente", null));
    }


}
