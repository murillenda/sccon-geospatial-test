package com.github.murillenda.sccon.geospatial.api.dto.input;

import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record PessoaPatchInputDTO(

        String nome,

        @Past
        LocalDate dataNascimento,

        @Past
        LocalDate dataAdmissao

) {}