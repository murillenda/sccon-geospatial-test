package com.github.murillenda.sccon.geospatial.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PessoaExistenteException extends RuntimeException {

    public PessoaExistenteException(String message) {
        super(message);
    }

    public PessoaExistenteException(Long pessoaId) {
        this(String.format("Já existe uma pessoa cadastrada com o código %d", pessoaId));
    }

}
