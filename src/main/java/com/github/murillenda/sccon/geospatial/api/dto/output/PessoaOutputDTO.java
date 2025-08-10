package com.github.murillenda.sccon.geospatial.api.dto.output;

import java.time.LocalDate;

public record PessoaOutputDTO(

        Long id,
        String nome,
        LocalDate dataNascimento,
        LocalDate dataAdmissao

) {}
