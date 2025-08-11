package com.github.murillenda.sccon.geospatial.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pessoa {

    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    private LocalDate dataNascimento;

    private LocalDate dataAdmissao;

    public BigDecimal calcularSalarioAtual(LocalDate dataReferencia) {
        long anosDeEmpresa = ChronoUnit.YEARS.between(this.dataAdmissao, dataReferencia);

        BigDecimal salarioAtual = new BigDecimal("1558.00");
        BigDecimal aumentoPercentual = new BigDecimal("0.18");
        BigDecimal aumentoFixo = new BigDecimal("500.00");

        for (int i = 0; i < anosDeEmpresa; i++) {
            BigDecimal aumento = salarioAtual.multiply(aumentoPercentual);
            salarioAtual = salarioAtual.add(aumento).add(aumentoFixo);
        }

        return salarioAtual.setScale(2, RoundingMode.HALF_UP);
    }

    public long calcularIdadeEmAnos(LocalDate dataReferencia) {
        return ChronoUnit.YEARS.between(this.dataNascimento, dataReferencia);
    }

    public long calcularIdadeEmMeses(LocalDate dataReferencia) {
        return ChronoUnit.MONTHS.between(this.dataNascimento, dataReferencia);
    }

    public long calcularIdadeEmDias(LocalDate dataReferencia) {
        return ChronoUnit.DAYS.between(this.dataNascimento, dataReferencia);
    }
}
