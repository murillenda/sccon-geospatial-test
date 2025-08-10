package com.github.murillenda.sccon.geospatial.domain.repository;

import com.github.murillenda.sccon.geospatial.domain.model.Pessoa;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
public class PessoaRepository {

    private final Map<Long, Pessoa> pessoasEmMemoria = new ConcurrentHashMap<>();

    private final AtomicLong sequence = new AtomicLong(0);

    @PostConstruct
    public void popularDadosIniciais() {
        log.info("Populando mapa em memória com dados iniciais...");
        save(new Pessoa(null, "José da Silva", LocalDate.of(2000, 4, 6), LocalDate.of(2020, 5, 10)));
        save(new Pessoa(null, "Maria Souza", LocalDate.of(1985, 8, 22), LocalDate.of(2018, 3, 5)));
        save(new Pessoa(null, "Carlos Pereira", LocalDate.of(2000, 2, 28), LocalDate.of(2022, 11, 20)));
        log.info("Dados iniciais populados com sucesso: {}", pessoasEmMemoria);
    }

    public Collection<Pessoa> findAll() {
        return pessoasEmMemoria.values();
    }

    public boolean existsById(Long id) {
        return pessoasEmMemoria.containsKey(id);
    }

    public Long findMaxId() {
        return pessoasEmMemoria.keySet().stream()
                .max(Long::compareTo)
                .orElse(0L);
    }

    public Pessoa save(Pessoa pessoa) {
        if (pessoa.getId() == null) {
            pessoa.setId(sequence.incrementAndGet());
        }
        pessoasEmMemoria.put(pessoa.getId(), pessoa);
        return pessoa;
    }

    public void deleteById(Long id) {
        pessoasEmMemoria.remove(id);
    }

    public Optional<Pessoa> findById(Long id) {
        return Optional.ofNullable(pessoasEmMemoria.get(id));
    }

}
