package com.github.murillenda.sccon.geospatial.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PessoaNaoEncontradaException extends RuntimeException {

    public PessoaNaoEncontradaException(String message) {
        super(message);
    }

    public PessoaNaoEncontradaException(Long pessoaId) {
        this(String.format("Não existe uma pessoa com o código %d", pessoaId));
    }

}
