package com.github.murillenda.sccon.geospatial.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

    public String calcularIdadeEm(FormatoIdade formato) {
        LocalDate hoje = LocalDate.now();

        return switch (formato) {
            case YEARS -> calcularIdadeEmAnos(hoje);
            case MONTHS -> calcularIdadeEmMeses(hoje);
            case DAYS -> calcularIdadeEmDias(hoje);
        };
    }

    private String calcularIdadeEmDias(LocalDate hoje) {
        return ChronoUnit.DAYS.between(this.dataNascimento, hoje) + " dias";
    }

    private String calcularIdadeEmMeses(LocalDate hoje) {
        return ChronoUnit.MONTHS.between(this.dataNascimento, hoje) + " meses";
    }

    private String calcularIdadeEmAnos(LocalDate hoje) {
        return ChronoUnit.YEARS.between(this.dataNascimento, hoje) + " anos";
    }
}
