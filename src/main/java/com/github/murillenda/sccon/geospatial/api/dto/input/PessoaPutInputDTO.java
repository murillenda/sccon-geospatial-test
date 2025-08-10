package com.github.murillenda.sccon.geospatial.api.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

/**
 DTO de entrada idêntico ao de POST, porém criado para facilitar melhor entendimento e facilitação de manutenção futura
 */
public record PessoaPutInputDTO(

        @NotBlank
        String nome,

        @NotNull
        @Past
        LocalDate dataNascimento,

        @NotNull
        @Past
        LocalDate dataAdmissao

) {}