package com.github.murillenda.sccon.geospatial.domain.service;

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

}
