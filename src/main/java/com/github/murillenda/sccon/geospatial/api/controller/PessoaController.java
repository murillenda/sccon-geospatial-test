package com.github.murillenda.sccon.geospatial.api.controller;

import com.github.murillenda.sccon.geospatial.api.assembler.PessoaMapper;
import com.github.murillenda.sccon.geospatial.api.dto.input.PessoaPatchInputDTO;
import com.github.murillenda.sccon.geospatial.api.dto.input.PessoaPostInputDTO;
import com.github.murillenda.sccon.geospatial.api.dto.input.PessoaPutInputDTO;
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        pessoaService.deletarPessoa(id);
    }

    @PutMapping("/{id}")
    public PessoaOutputDTO atualizar(@PathVariable Long id, @Valid @RequestBody PessoaPutInputDTO inputDTO) {
        var pessoaAtualizada = pessoaMapper.toDomainObject(inputDTO);
        var pessoaSalva = pessoaService.atualizarPessoa(id, pessoaAtualizada);
        return pessoaMapper.toOutputDTO(pessoaSalva);
    }

    /**
     * Abordagem pragmática
     */
    @PatchMapping("/{id}")
    public PessoaOutputDTO atualizarParcialmente(@PathVariable Long id, @RequestBody PessoaPatchInputDTO inputDTO) {
        var pessoaSalva = pessoaService.atualizarParcialmente(id, inputDTO);
        return pessoaMapper.toOutputDTO(pessoaSalva);
    }


}
