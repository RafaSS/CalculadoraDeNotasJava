package com.example.demo.controller;

import com.example.demo.exception.BusinessException;
import com.example.demo.modelo.Materia;
import com.example.demo.modelo.Professor;
import com.example.demo.repository.MateriaRepository;
import com.example.demo.repository.MatriculaRepository;
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
    @Autowired
    private MatriculaRepository matriculaRepository;

    @BeforeEach
    void setup() {
        matriculaRepository.deleteAll();
        materiaRepository.deleteAll();
        professorRepository.deleteAll();



        professor = new Professor(null, "Maria", null);

    }

    @Test
    void deveCriarMateria() throws Exception {
        Materia materia = new Materia(null, "Matematica", professor, null);
        professor.setMaterias(Set.of(materia));
        professor = professorRepository.save(professor);
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
        professor.setMaterias(Set.of(materia));
        professor = professorRepository.save(professor);
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
        Materia materia = new Materia(1L, "Inexistente", professorInexistente, null);
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

    @Test
    void deveAtualizarMateria() throws Exception {
        Materia materia = new Materia(null, "Matematica", professor, null);
        professor.setMaterias(Set.of(materia));
        professor = professorRepository.save(professor);
        materia = materiaRepository.save(materia);

        materia.setNome("Portugues");
        String materiaJson = objectMapper.writeValueAsString(materia);

        mockMvc.perform(put("/api/materia/" + materia.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(materiaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Portugues"))
                .andExpect(jsonPath("$.professor.id").value(professor.getId()));
    }

    @Test
    void deveDeletarMateria() throws Exception {
        Materia materia = new Materia(null, "Matematica", professor, null);
        professor.setMaterias(Set.of(materia));
        professor = professorRepository.save(professor);
        materia = materiaRepository.save(materia);

        mockMvc.perform(delete("/api/materia/" + materia.getId()))
                .andExpect(status().isOk());
    }
}