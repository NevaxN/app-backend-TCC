package com.app.src.auth.services;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.app.src.dto.PerfilUsuario;
import org.springframework.stereotype.Service;

import com.app.src.auth.models.UsuarioDetailsImpl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JwtTokenService {
    
    private static final String SECRET_KEY = "4Z^XrroxR@dWxqf$mTTKwW$!@#qGr4P";
    private static final String ISSUER = "admin";

    public String generateToken(UsuarioDetailsImpl usuario, PerfilUsuario perfilUsuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirationDate())
                    .withSubject(usuario.getUsername())
                    .withClaim("tipo_usuario", perfilUsuario.tipo())
                    .withClaim("id_usuario", perfilUsuario.id())
                    .withClaim("conta_verificada", usuario.getUsuario().isEmailVerificado())
                    .sign(algorithm);

        } catch(JWTCreationException exception){
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token inválido ou expirado");
        }
    }

    private Instant creationDate(){
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    private Instant expirationDate(){
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(4).toInstant();
    }
}