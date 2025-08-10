package com.github.murillenda.sccon.geospatial.domain.model;

import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Pessoa {

    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    private LocalDate dataNascimento;

    private LocalDate dataAdmissao;

    public Pessoa(Long id, String nome, LocalDate dataNascimento, LocalDate dataAdmissao) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.dataAdmissao = dataAdmissao;
    }

}
