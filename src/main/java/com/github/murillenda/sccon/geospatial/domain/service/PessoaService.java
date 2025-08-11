package com.github.murillenda.sccon.geospatial.domain.service;

import com.github.murillenda.sccon.geospatial.api.assembler.PessoaMapper;
import com.github.murillenda.sccon.geospatial.api.dto.input.PessoaPatchInputDTO;
import com.github.murillenda.sccon.geospatial.api.dto.output.PessoaIdadeOutputDTO;
import com.github.murillenda.sccon.geospatial.api.dto.output.PessoaSalarioOutputDTO;
import com.github.murillenda.sccon.geospatial.domain.exception.PessoaExistenteException;
import com.github.murillenda.sccon.geospatial.domain.exception.PessoaNaoEncontradaException;
import com.github.murillenda.sccon.geospatial.domain.model.FormatoIdade;
import com.github.murillenda.sccon.geospatial.domain.model.FormatoSalario;
import com.github.murillenda.sccon.geospatial.domain.model.Pessoa;
import com.github.murillenda.sccon.geospatial.domain.repository.PessoaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class PessoaService {

    private static final LocalDate DATA_REFERENCIA_TESTE = LocalDate.of(2023, 2, 7);
    private static final BigDecimal SALARIO_MINIMO_2023 = new BigDecimal("1302.00");
    private static final NumberFormat FORMATADOR_BRL = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));


    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, PessoaMapper pessoaMapper) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaMapper = pessoaMapper;
    }

    public List<Pessoa> listarPessoasOrdenadasPorNome() {
        return pessoaRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Pessoa::getNome))
                .toList();
    }

    public Pessoa criarPessoa(Pessoa pessoa) {

        if (pessoa.getId() != null && pessoaRepository.existsById(pessoa.getId())) {
            throw new PessoaExistenteException(pessoa.getId());
        }

        if (pessoa.getId() == null) {
            long proximoId = pessoaRepository.findMaxId() + 1;
            pessoa.setId(proximoId);
        }

        return pessoaRepository.save(pessoa);
    }

    public void deletarPessoa(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new PessoaNaoEncontradaException(id);
        }
        pessoaRepository.deleteById(id);
    }

    public Pessoa atualizarPessoa(Long id, Pessoa pessoaAtualizada) {
        // Garante que o ‘ID’ da URL seja o mesmo do objeto
        pessoaAtualizada.setId(id);
        if (!pessoaRepository.existsById(id)) {
            throw new PessoaNaoEncontradaException(id);
        }
        return pessoaRepository.save(pessoaAtualizada);
    }

    public Pessoa atualizarParcialmente(Long id, PessoaPatchInputDTO inputDTO) {
        Pessoa pessoaAtual = this.buscarPorId(id);
        pessoaMapper.atualizarPessoaFromPatchDTO(inputDTO, pessoaAtual);
        return pessoaRepository.save(pessoaAtual);
    }

    public Pessoa buscarPorId(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException(id));
    }

    public PessoaIdadeOutputDTO buscarIdadeFormatada(Long id, FormatoIdade formato) {
        Pessoa pessoa = this.buscarPorId(id);

        String idadeFormatada = switch (formato) {
            case YEARS -> pessoa.calcularIdadeEmAnos(DATA_REFERENCIA_TESTE) + " anos";
            case MONTHS -> pessoa.calcularIdadeEmMeses(DATA_REFERENCIA_TESTE) + " meses";
            case DAYS -> pessoa.calcularIdadeEmDias(DATA_REFERENCIA_TESTE) + " dias";
        };

        return new PessoaIdadeOutputDTO(pessoa.getNome(), idadeFormatada);
    }

    public PessoaSalarioOutputDTO buscarSalarioFormatado(Long id, FormatoSalario formato) {
        Pessoa pessoa = this.buscarPorId(id);

        String salarioFormatado = switch (formato) {
            case FULL -> {
                BigDecimal salario = pessoa.calcularSalarioAtual(DATA_REFERENCIA_TESTE);
                yield FORMATADOR_BRL.format(salario);
            }
            case MIN -> {
                BigDecimal salarioAtual = pessoa.calcularSalarioAtual(DATA_REFERENCIA_TESTE);
                BigDecimal emSalariosMinimos = salarioAtual.divide(SALARIO_MINIMO_2023, 2, RoundingMode.UP);
                yield emSalariosMinimos + " salários mínimos";
            }
        };

        return new PessoaSalarioOutputDTO(pessoa.getNome(), salarioFormatado);
    }

}
