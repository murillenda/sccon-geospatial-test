package com.github.murillenda.sccon.geospatial.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.murillenda.sccon.geospatial.api.dto.input.PessoaPatchInputDTO;
import com.github.murillenda.sccon.geospatial.api.dto.input.PessoaPostInputDTO;
import com.github.murillenda.sccon.geospatial.domain.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PessoaControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PessoaRepository pessoaRepository;

    @BeforeEach
    void setUp() {
        pessoaRepository.deleteAll();
        pessoaRepository.popularDadosIniciais();
    }

    @Test
    @DisplayName("GET /person - Deve retornar a lista de pessoas ordenada por nome e status 200")
    void deveRetornarListaDePessoasOrdenada() throws Exception {
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].nome").value("José da Silva"))
                .andExpect(jsonPath("$[2].nome").value("Maria Souza"));
    }

    @Test
    @DisplayName("GET /person/{id} - Deve retornar uma pessoa quando o ID existe e status 200")
    void deveRetornarPessoaPorId_QuandoIdExiste() throws Exception {
        mockMvc.perform(get("/person/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("José da Silva"));
    }

    @Test
    @DisplayName("GET /person/{id} - Deve retornar status 404 quando o ID não existe")
    void deveRetornarStatus404_QuandoBuscarPessoaPorIdInexistente() throws Exception {
        mockMvc.perform(get("/person/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /person - Deve criar uma pessoa e retornar status 201")
    void deveCriarPessoa_QuandoDadosValidos() throws Exception {
        var inputDTO = new PessoaPostInputDTO(null, "Ana Clara",
                LocalDate.of(1995, 7, 30),
                LocalDate.of(2023, 1, 15));

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.nome").value("Ana Clara"));
    }

    @Test
    @DisplayName("POST /person - Deve retornar status 409 quando o ID já existe")
    void deveRetornarStatus409_QuandoCriarPessoaComIdExistente() throws Exception {
        var inputDTO = new PessoaPostInputDTO(1L, "Pessoa Conflitante",
                LocalDate.of(1999, 1, 1),
                LocalDate.of(2024, 1, 1));

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("POST /person - Deve retornar status 400 para dados inválidos (ex: nome em branco)")
    void deveRetornarStatus400_QuandoCriarPessoaComDadosInvalidos() throws Exception {
        var inputDTO = new PessoaPostInputDTO(null, "",
                LocalDate.of(1995, 7, 30),
                LocalDate.of(2023, 1, 15));

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Um ou mais campos estão inválidos."))
                .andExpect(jsonPath("$.fields[0].name").value("nome"));
    }

    @Test
    @DisplayName("DELETE /person/{id} - Deve deletar a pessoa e retornar status 204")
    void deveDeletarPessoa_QuandoIdExiste() throws Exception {
        mockMvc.perform(delete("/person/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/person/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PATCH /person/{id} - Deve atualizar parcialmente a pessoa e retornar status 200")
    void deveAtualizarParcialmentePessoa_QuandoIdExiste() throws Exception {
        var patchDTO = new PessoaPatchInputDTO("José da Silva Santos", null, null);

        mockMvc.perform(patch("/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("José da Silva Santos"))
                .andExpect(jsonPath("$.dataNascimento").value("2000-04-06"));
    }

    @Test
    @DisplayName("GET /person/{id}/age - Deve retornar a idade em anos e status 200")
    void deveRetornarIdadeEmAnos_QuandoFormatoValido() throws Exception {
        mockMvc.perform(get("/person/1/age?output=years"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idade").value("22 anos"));
    }

    @Test
    @DisplayName("GET /person/{id}/age - Deve retornar status 400 para formato de idade inválido")
    void deveRetornarStatus400_QuandoFormatoDeIdadeInvalido() throws Exception {
        mockMvc.perform(get("/person/1/age?output=weeks"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /person/{id}/salary - Deve retornar o salário em formato 'full' e status 200")
    void deveRetornarSalarioEmFormatoFull_QuandoFormatoValido() throws Exception {
        mockMvc.perform(get("/person/1/salary?output=full"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salario").value("R$\u00A03.259,36"));
    }

    @Test
    @DisplayName("GET /person/{id}/salary - Deve retornar o salário em formato 'min' e status 200")
    void deveRetornarSalarioEmFormatoMin_QuandoFormatoValido() throws Exception {
        mockMvc.perform(get("/person/1/salary?output=min"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salario").value("2.51 salários mínimos"));
    }

}
