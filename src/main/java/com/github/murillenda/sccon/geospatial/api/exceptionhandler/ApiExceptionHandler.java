package com.github.murillenda.sccon.geospatial.api.exceptionhandler;

import com.github.murillenda.sccon.geospatial.domain.exception.PessoaExistenteException;
import com.github.murillenda.sccon.geospatial.domain.exception.PessoaNaoEncontradaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    // Handler para as nossas exceções de 'não encontrado' (404)
    @ExceptionHandler(PessoaNaoEncontradaException.class)
    public ResponseEntity<Object> handlePessoaNaoEncontrada(PessoaNaoEncontradaException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        var errorResponse = new ErrorResponse(
                status.value(),
                LocalDateTime.now(),
                "Recurso não encontrado",
                ex.getMessage(),
                null
        );
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    // Handler para as nossas exceções de 'conflito' (409)
    @ExceptionHandler(PessoaExistenteException.class)
    public ResponseEntity<Object> handlePessoaExistente(PessoaExistenteException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        var errorResponse = new ErrorResponse(
                status.value(),
                LocalDateTime.now(),
                "Recurso já existente",
                ex.getMessage(),
                null
        );
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    // Handler para a validação de argumentos inválidos (400)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        var errorResponse = new ErrorResponse(
                status.value(),
                LocalDateTime.now(),
                "Requisição inválida",
                ex.getMessage(),
                null
        );
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    // Handler para os erros de validação do @Valid (400)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        List<ErrorResponse.ErrorField> fields = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ErrorResponse.ErrorField(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()))
                .toList();

        var errorResponse = new ErrorResponse(
                status.value(),
                LocalDateTime.now(),
                "Um ou mais campos estão inválidos.",
                "Faça o preenchimento correto e tente novamente.",
                fields
        );
        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', que é de um tipo inválido. " +
                        "Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        var errorResponse = new ErrorResponse(
                status.value(),
                LocalDateTime.now(),
                "Parâmetro inválido",
                detail,
                null
        );

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String detail = String.format("O parâmetro obrigatório '%s' não foi informado na requisição.", ex.getParameterName());

        var errorResponse = new ErrorResponse(
                status.value(),
                LocalDateTime.now(),
                "Parâmetro obrigatório ausente",
                detail,
                null
        );

        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }
}