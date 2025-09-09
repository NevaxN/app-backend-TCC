package com.app.src.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pesquisador_id", nullable = false)
    private Pesquisador pesquisador;

    @ElementCollection
    @CollectionTable(
        name = "tag_lista_tags",
        joinColumns = @JoinColumn(name = "tag_id")
    )
    @Column(name = "tag")
    private List<String> listaTags;
}
