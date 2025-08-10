package com.github.murillenda.sccon.geospatial.api.controller;

import com.github.murillenda.sccon.geospatial.api.assembler.PessoaMapper;
import com.github.murillenda.sccon.geospatial.api.dto.output.PessoaOutputDTO;
import com.github.murillenda.sccon.geospatial.domain.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
