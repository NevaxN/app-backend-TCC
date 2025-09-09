package com.app.src.controllers;

import com.app.src.dto.CriarUsuarioDTO;
import com.app.src.dto.LoginUsuarioDTO;
import com.app.src.dto.ResgatarJWTTokenDTO;
import com.app.src.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<ResgatarJWTTokenDTO> authenticateUser(@RequestBody LoginUsuarioDTO loginUsuarioDTO) {

        ResgatarJWTTokenDTO token = usuarioService.authenticateUser(loginUsuarioDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/salvarUsuario")
    public ResponseEntity<Void> criarUsuario(@RequestBody CriarUsuarioDTO criarUsuarioDTO) {
        usuarioService.createUser(criarUsuarioDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest(){
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/cliente")
    public ResponseEntity<String> getClienteAuthenticationTest() {
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/administrador")
    public ResponseEntity<String> getAdminAuthenticationTest(){
        return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
    }

}
