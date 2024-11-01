package com.example.demo.controller;

import com.example.demo.modelo.Aluno;
import com.example.demo.modelo.Materia;
import com.example.demo.modelo.Professor;
import com.example.demo.repository.AlunoRepository;
import com.example.demo.repository.MateriaRepository;
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
class MateriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    void deveCriarMateria() throws Exception {
        Professor professor = new Professor(1L, "Maria", null);
        Materia materia = new Materia(null, "Matematica", professor, null);
        String materiaJson = objectMapper.writeValueAsString(materia);

        mockMvc.perform(post("/api/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(materiaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Matematica"));
    }

    @Test
    void deveBuscarMateria() throws Exception {
        Professor professor = new Professor(null, "Maria", null);
        Materia materia = new Materia(null, "Matematica", professor, null);
        materiaRepository.save(materia);

        mockMvc.perform(get("/api/materia/" + materia.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Matematica"));
    }

    @Test
    void deveRetornarNotFoundAoBuscarMateriaInexistente() throws Exception {
        mockMvc.perform(get("/api/materia/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornarBadRequestAoCriarMateriaComProfessorInexistente() throws Exception {
        Professor professor = new Professor(1L, "Maria", null);
        Materia materia = new Materia(null, "Matematica", professor, null);
        String materiaJson = objectMapper.writeValueAsString(materia);

        mockMvc.perform(post("/api/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(materiaJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarBadRequestAoCriarMateriaComProfessorNulo() throws Exception {
        Materia materia = new Materia(null, "Matematica", null, null);
        String materiaJson = objectMapper.writeValueAsString(materia);

        mockMvc.perform(post("/api/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(materiaJson))
                .andExpect(status().isBadRequest());
    }



}
