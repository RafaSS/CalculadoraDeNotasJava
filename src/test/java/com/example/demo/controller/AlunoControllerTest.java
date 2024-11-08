package com.example.demo.controller;

import com.example.demo.modelo.Aluno;
import com.example.demo.repository.AlunoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        alunoRepository.deleteAll();
    }


    @Test
    void deveCriarAluno() throws Exception {
        Aluno aluno = new Aluno(null, "Maria", null);
        String alunoJson = objectMapper.writeValueAsString(aluno);

        mockMvc.perform(post("/api/aluno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(alunoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria"));
    }

    @Test
    void deveBuscarAluno() throws Exception {
        Aluno aluno = new Aluno(null, "Maria", null);
        alunoRepository.save(aluno);

        mockMvc.perform(get("/api/aluno/" + aluno.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria"));
    }

    @Test
    void deveAtualizarAluno() throws Exception {
        Aluno aluno = new Aluno(null, "Maria", null);
        alunoRepository.save(aluno);

        aluno.setNome("João");
        String alunoJson = objectMapper.writeValueAsString(aluno);

        mockMvc.perform(put("/api/aluno/" + aluno.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(alunoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void deveDeletarAluno() throws Exception {
        Aluno aluno = new Aluno(null, "Maria", null);
        alunoRepository.save(aluno);

        mockMvc.perform(delete("/api/aluno/" + aluno.getId()))
                .andExpect(status().isOk());
    }





}
