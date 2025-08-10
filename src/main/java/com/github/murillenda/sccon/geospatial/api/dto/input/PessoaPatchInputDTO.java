package com.github.murillenda.sccon.geospatial.api.dto.input;

import java.time.LocalDate;

public record PessoaPatchInputDTO(

        String nome,
        LocalDate dataNascimento,
        LocalDate dataAdmissao

) {}