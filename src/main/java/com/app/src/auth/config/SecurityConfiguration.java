package com.app.src.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.src.auth.filters.UserAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;
    
    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
        "/api/usuarios/login",
        "/api/usuarios/salvarUsuario",
        "/api/usuarios/verificarEmail",
        "/api/redefinicao/enviar",
        "/api/redefinicao/validar",
        "/api/redefinicao/alterar",
        "/api/upload",
        "/api/tags/salvarTag",
    };

    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
        "/api/email/enviarVerificacao",
        "/api/pesquisadores/**",
        "/api/auth/refresh-token",
        "/api/seguidores/**",
        "/api/recomendacoes/**",
        "/api/empresas/**",
        "/api/tags/alterarTag/**",
        "/api/usuarios/alterarLogin",
        "/api/formacoes/salvarFormacao"
    };

    public static final String [] ENDPOINTS_CUSTOMER = {
        // "/api/pesquisadores/**",
        // "/api/seguidores/**",
        // "/api/recomendacoes/**",
        "/api/dadosPesquisador/**",
        "/api/usuarios/listarUsuario/**",
        "/api/tags/listarTag/**"
    };

    public static final String [] ENDPOINTS_ADMIN = {
        "/api/usuarios/test/administrador"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                    .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                    .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADM")
                    .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("USUARIO")
                    .anyRequest().denyAll()
                )
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}