package com.app.src.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.src.auth.models.UsuarioDetailsImpl;
import com.app.src.models.Usuario;
import com.app.src.repositories.UsuarioRepository;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        
        return new UsuarioDetailsImpl(usuario);
    }
}
