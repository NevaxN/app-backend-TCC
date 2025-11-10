package com.app.src.auth.controllers;

import com.app.src.auth.models.UsuarioDetailsImpl;
import com.app.src.auth.services.JwtTokenService;
import com.app.src.auth.services.PerfilUsuarioService;
import com.app.src.dto.PerfilUsuario;
import com.app.src.dto.ResgatarJWTTokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PerfilUsuarioService perfilUsuarioService;

    @PostMapping("/refresh-token")
    public ResponseEntity<ResgatarJWTTokenDTO> refreshToken (@RequestHeader("Authorization") String authHeader) {

        try {
            String token = authHeader.replace("Bearer ", "");
            String username = jwtTokenService.getSubjectFromToken(token);

            if (username == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            UsuarioDetailsImpl usuarioDetails = (UsuarioDetailsImpl) userDetailsService.loadUserByUsername(username);
            PerfilUsuario perfilUsuario = perfilUsuarioService.buscarPerfilUsuario(usuarioDetails);

            String novoToken = jwtTokenService.generateToken(
                    usuarioDetails,
                    perfilUsuario
            );

            return ResponseEntity.ok(new ResgatarJWTTokenDTO(novoToken));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

}
