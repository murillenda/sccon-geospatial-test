package com.github.murillenda.sccon.geospatial.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Pessoa {

    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    private LocalDate dataNascimento;

    private LocalDate dataAdmissao;

    private static final NumberFormat FORMATADOR_BRL = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));

    public Pessoa(Long id, String nome, LocalDate dataNascimento, LocalDate dataAdmissao) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.dataAdmissao = dataAdmissao;
    }

    public String calcularSalario(FormatoSalario formato) {
        BigDecimal salarioMinimo2023 = new BigDecimal("1302.00");

        return switch (formato) {
            case FULL -> FORMATADOR_BRL.format(this.calcularSalarioAtual());
            case MIN -> this.getSalarioEmSalariosMinimos(salarioMinimo2023) + " salários minimos";
        };
    }

    private BigDecimal calcularSalarioAtual() {
        LocalDate hoje = LocalDate.now();

        long anosDeEmpresa = ChronoUnit.YEARS.between(this.dataAdmissao, hoje);

        BigDecimal salarioAtual = new BigDecimal("1558.00");
        BigDecimal aumentoPercentual = new BigDecimal("0.18");
        BigDecimal aumentoFixo = new BigDecimal("500.00");

        for (int i = 0; i < anosDeEmpresa; i++) {
            BigDecimal aumento = salarioAtual.multiply(aumentoPercentual);
            salarioAtual = salarioAtual.add(aumento).add(aumentoFixo);
        }

        return salarioAtual.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getSalarioEmSalariosMinimos(BigDecimal salarioMinimo) {
        BigDecimal salarioAtual = this.calcularSalarioAtual();
        return salarioAtual.divide(salarioMinimo, 2, RoundingMode.UP);
    }



    public String calcularIdadeEm(FormatoIdade formato) {
        LocalDate hoje = LocalDate.now();

        return switch (formato) {
            case YEARS -> calcularIdadeEmAnos(hoje);
            case MONTHS -> calcularIdadeEmMeses(hoje);
            case DAYS -> calcularIdadeEmDias(hoje);
        };
    }

    private String calcularIdadeEmDias(LocalDate hoje) {
        return ChronoUnit.DAYS.between(this.dataNascimento, hoje) + " dias";
    }

    private String calcularIdadeEmMeses(LocalDate hoje) {
        return ChronoUnit.MONTHS.between(this.dataNascimento, hoje) + " meses";
    }

    private String calcularIdadeEmAnos(LocalDate hoje) {
        return ChronoUnit.YEARS.between(this.dataNascimento, hoje) + " anos";
    }
}
