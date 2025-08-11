package com.github.murillenda.sccon.geospatial.api.controller;

import com.github.murillenda.sccon.geospatial.api.assembler.PessoaMapper;
import com.github.murillenda.sccon.geospatial.api.dto.input.PessoaPatchInputDTO;
import com.github.murillenda.sccon.geospatial.api.dto.input.PessoaPostInputDTO;
import com.github.murillenda.sccon.geospatial.api.dto.input.PessoaPutInputDTO;
import com.github.murillenda.sccon.geospatial.api.dto.output.PessoaIdadeOutputDTO;
import com.github.murillenda.sccon.geospatial.api.dto.output.PessoaOutputDTO;
import com.github.murillenda.sccon.geospatial.api.dto.output.PessoaSalarioOutputDTO;
import com.github.murillenda.sccon.geospatial.domain.model.FormatoIdade;
import com.github.murillenda.sccon.geospatial.domain.model.FormatoSalario;
import com.github.murillenda.sccon.geospatial.domain.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Pessoas", description = "Endpoints para Gerenciamento de Pessoas")
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

    @Operation(summary = "Lista todas as pessoas", description = "Retorna uma lista de todas as pessoas cadastradas, ordenadas por nome.")
    @GetMapping
    public List<PessoaOutputDTO> listar() {
        var pessoas = pessoaService.listarPessoasOrdenadasPorNome();
        return pessoaMapper.toOutputDTOList(pessoas);
    }

    @Operation(summary = "Busca uma pessoa por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public PessoaOutputDTO buscarPorId(@PathVariable Long id) {
        var pessoa = pessoaService.buscarPorId(id);
        return pessoaMapper.toOutputDTO(pessoa);
    }

    @Operation(summary = "Calcula a idade de uma pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cálculo realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato de saída inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada", content = @Content)
    })
    @GetMapping("/{id}/age")
    public PessoaIdadeOutputDTO buscarIdade(@PathVariable Long id, @RequestParam FormatoIdade output) {
        return pessoaService.buscarIdadeFormatada(id, output);
    }

    @Operation(summary = "Calcula o salário de uma pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cálculo realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato de saída inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada", content = @Content)
    })
    @GetMapping("/{id}/salary")
    public PessoaSalarioOutputDTO buscarSalario(@PathVariable Long id, @RequestParam FormatoSalario output) {
        return pessoaService.buscarSalarioFormatado(id, output);
    }

    @Operation(summary = "Cria uma nova pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Pessoa com o ID especificado já existe", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PessoaOutputDTO> criar(@Valid @RequestBody PessoaPostInputDTO inputDTO) {
        var pessoa = pessoaMapper.toDomainObject(inputDTO);
        var pessoaSalva = pessoaService.criarPessoa(pessoa);
        var pessoaOutput = pessoaMapper.toOutputDTO(pessoaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaOutput);
    }

    @Operation(summary = "Atualiza uma pessoa existente (substituição total)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public PessoaOutputDTO atualizar(@PathVariable Long id, @Valid @RequestBody PessoaPutInputDTO inputDTO) {
        var pessoaAtualizada = pessoaMapper.toDomainObject(inputDTO);
        var pessoaSalva = pessoaService.atualizarPessoa(id, pessoaAtualizada);
        return pessoaMapper.toOutputDTO(pessoaSalva);
    }

    /**
     * Abordagem pragmática
     */
    @Operation(summary = "Atualiza parcialmente uma pessoa existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos (ex: nome com menos de 2 caracteres)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada", content = @Content)
    })
    @PatchMapping("/{id}")
    public PessoaOutputDTO atualizarParcialmente(@PathVariable Long id, @Valid @RequestBody PessoaPatchInputDTO inputDTO) {
        var pessoaSalva = pessoaService.atualizarParcialmente(id, inputDTO);
        return pessoaMapper.toOutputDTO(pessoaSalva);
    }

    @Operation(summary = "Deleta uma pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pessoa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        pessoaService.deletarPessoa(id);
    }

}
