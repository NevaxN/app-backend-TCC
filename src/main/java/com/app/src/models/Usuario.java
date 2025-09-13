package com.app.src.models;

import java.util.List;

import com.app.src.auth.enums.Role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false, unique = true)
    private String password;

    // Novo campo para o tipo de usuário
    @Enumerated(EnumType.STRING) // Grava o nome do enum no banco, em vez do índice (0)
    @Column(name = "tipo_usuario", nullable = false)
    private TipoUsuario tipoUsuario;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles;
}
