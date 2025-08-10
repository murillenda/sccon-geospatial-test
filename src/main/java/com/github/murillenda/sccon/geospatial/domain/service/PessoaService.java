package com.github.murillenda.sccon.geospatial.domain.service;

import com.github.murillenda.sccon.geospatial.domain.exception.PessoaExistenteException;
import com.github.murillenda.sccon.geospatial.domain.exception.PessoaNaoEncontradaException;
import com.github.murillenda.sccon.geospatial.domain.model.Pessoa;
import com.github.murillenda.sccon.geospatial.domain.repository.PessoaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
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

}
