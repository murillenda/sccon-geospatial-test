package com.github.murillenda.sccon.geospatial.api.controller;

import com.github.murillenda.sccon.geospatial.api.assembler.PessoaMapper;
import com.github.murillenda.sccon.geospatial.api.dto.input.PessoaPostInputDTO;
import com.github.murillenda.sccon.geospatial.api.dto.output.PessoaOutputDTO;
import com.github.murillenda.sccon.geospatial.domain.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PessoaController {

    private final PessoaService pessoaService;
    private final PessoaMapper pessoaMapper;

    @Autowired
    public PessoaController(PessoaService pessoaService, PessoaMapper pessoaMapper) {
        this.pessoaService = pessoaService;
        this.pessoaMapper = pessoaMapper;
    }

    @GetMapping
    public List<PessoaOutputDTO> listar() {
        var pessoas = pessoaService.listarPessoasOrdenadasPorNome();
        return pessoaMapper.toOutputDTOList(pessoas);
    }

    @PostMapping
    public ResponseEntity<PessoaOutputDTO> criar(@Valid @RequestBody PessoaPostInputDTO inputDTO) {
        var pessoa = pessoaMapper.toDomainObject(inputDTO);
        var pessoaSalva = pessoaService.criarPessoa(pessoa);
        var pessoaOutput = pessoaMapper.toOutputDTO(pessoaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaOutput);
    }
}
