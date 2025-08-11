package com.github.murillenda.sccon.geospatial.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PessoaTests {

    private static final LocalDate DATA_REFERENCIA = LocalDate.of(2023, 2, 7);

    @Test
    @DisplayName("Deve calcular o salário corretamente conforme a regra do teste")
    void deveCalcularSalarioCorretamente() {
        Pessoa jose = new Pessoa(1L, "José da Silva",
                LocalDate.of(2000, 4, 6),
                LocalDate.of(2020, 5, 10));

        BigDecimal salarioCalculado = jose.calcularSalarioAtual(DATA_REFERENCIA);

        assertThat(salarioCalculado).isEqualTo(new BigDecimal("3259.36"));
    }

    @Test
    @DisplayName("Deve calcular a idade em anos corretamente")
    void deveCalcularIdadeEmAnosCorretamente() {
        Pessoa jose = new Pessoa(1L, "José da Silva",
                LocalDate.of(2000, 4, 6),
                LocalDate.of(2020, 5, 10));

        long idadeEmAnos = jose.calcularIdadeEmAnos(DATA_REFERENCIA);

        assertThat(idadeEmAnos).isEqualTo(22L);
    }
}
