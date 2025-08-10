package com.github.murillenda.sccon.geospatial.api.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record PessoaPostInputDTO(

        Long id,

        @NotBlank
        String nome,

        @NotNull
        @Past
        LocalDate dataNascimento,

        @NotNull
        @Past
        LocalDate dataAdmissao

){}