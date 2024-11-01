package com.example.demo.controller;

import com.example.demo.exception.BusinessException;
import com.example.demo.modelo.Materia;
import com.example.demo.modelo.Professor;
import com.example.demo.repository.MateriaRepository;
import com.example.demo.repository.ProfessorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    private ProfessorRepository professorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Professor professor;

    @BeforeEach
    void setup() {
        materiaRepository.deleteAll();
        professorRepository.deleteAll();


        professor = new Professor(null, "Maria", null);
        professor = professorRepository.save(professor);
    }

    @Test
    void deveCriarMateria() throws Exception {
        Materia materia = new Materia(null, "Matematica", professor, null);
        String materiaJson = objectMapper.writeValueAsString(materia);

        mockMvc.perform(post("/api/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(materiaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Matematica"))
                .andExpect(jsonPath("$.professor.id").value(professor.getId()));
    }

    @Test
    void deveBuscarMateria() throws Exception {
        Materia materia = new Materia(null, "Matematica", professor, null);
        materia = materiaRepository.save(materia);

        mockMvc.perform(get("/api/materia/" + materia.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Matematica"))
                .andExpect(jsonPath("$.professor.id").value(professor.getId()));
    }

    @Test
    void deveRetornarNotFoundAoBuscarMateriaInexistente() throws Exception {
        mockMvc.perform(get("/api/materia/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornarBadRequestAoCriarMateriaComProfessorInexistente() throws Exception {
        Professor professorInexistente = new Professor(99L, "Inexistente", null);
        Materia materia = new Materia(null, "Matematica", professorInexistente, null);
        String materiaJson = objectMapper.writeValueAsString(materia);

        mockMvc.perform(post("/api/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(materiaJson))
                .andExpect(status().isNotFound())  // Espera que o status seja 404 para professor inexistente
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BusinessException));
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