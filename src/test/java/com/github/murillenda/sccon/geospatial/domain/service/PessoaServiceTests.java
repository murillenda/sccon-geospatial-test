package com.github.murillenda.sccon.geospatial.domain.service;

import com.github.murillenda.sccon.geospatial.domain.exception.PessoaExistenteException;
import com.github.murillenda.sccon.geospatial.domain.model.Pessoa;
import com.github.murillenda.sccon.geospatial.domain.repository.PessoaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTests {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    @Test
    @DisplayName("Deve criar uma pessoa com sucesso quando o ID é nulo")
    void deveCriarPessoaComSucesso() {
        Pessoa pessoaSemId = new Pessoa(null, "Nova Pessoa", LocalDate.now(), LocalDate.now());
        Pessoa pessoaComId = new Pessoa(1L, "Nova Pessoa", LocalDate.now(), LocalDate.now());

        when(pessoaRepository.save(pessoaSemId)).thenReturn(pessoaComId);

        Pessoa pessoaSalva = pessoaService.criarPessoa(pessoaSemId);

        assertThat(pessoaSalva.getId()).isNotNull();
        assertThat(pessoaSalva.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar pessoa com ID que já existe")
    void deveLancarExcecaoAoCriarPessoaComIdExistente() {
        Pessoa pessoaComIdExistente = new Pessoa(1L, "Pessoa Existente", LocalDate.now(), LocalDate.now());

        when(pessoaRepository.existsById(1L)).thenReturn(true);

        assertThatThrownBy(() -> pessoaService.criarPessoa(pessoaComIdExistente))
                .isInstanceOf(PessoaExistenteException.class)
                .hasMessageContaining("Já existe uma pessoa cadastrada com o código 1");
    }

}
