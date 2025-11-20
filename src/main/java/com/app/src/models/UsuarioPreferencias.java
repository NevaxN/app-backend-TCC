package com.app.src.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario_preferencias")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioPreferencias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "exibir_contato")
    private boolean exibirContato;

    @Column(name = "exibir_localizacao")
    private boolean exibirLocalizacao;

    public UsuarioPreferencias(Usuario usuario, boolean exibirContato, boolean exibirLocalizacao) {
        this.usuario = usuario;
        this.exibirContato = exibirContato;
        this.exibirLocalizacao = exibirLocalizacao;
    }
}
