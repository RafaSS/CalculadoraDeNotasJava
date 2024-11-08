package com.example.demo.controller;

import com.example.demo.modelo.Materia;
import com.example.demo.modelo.Professor;
import com.example.demo.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProfessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Professor professor;
    @Autowired
    private NotaRepository notaRepository;
    @Autowired
    private MatriculaRepository matriculaRepository;
    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private AlunoRepository alunoRepository;

    @BeforeEach
    void setup() {

        notaRepository.deleteAll();
        matriculaRepository.deleteAll();
        materiaRepository.deleteAll();
        professorRepository.deleteAll();
        alunoRepository.deleteAll();

        professor = new Professor(null, "Maria", null);

    }

    @Test
    void deveCriarProfessor() throws Exception {
        mockMvc.perform(post("/api/professor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(professor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value(professor.getNome()));

    }

    @Test
    void deveBuscarProfessor() throws Exception {
        professor = professorRepository.save(professor);

        mockMvc.perform(get("/api/professor/" + professor.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value(professor.getNome()));
    }


    @Test
    void deveCriarProfessorComMaterias() throws Exception {
        Materia materia = new Materia(null, "Matematica", professor, null);
        professor.setMaterias(Set.of(materia));
        mockMvc.perform(post("/api/professor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(professor)))
                .andExpect(status().isOk());

    }

    @Test
    void deveAtualizarProfessor() throws Exception {
        professor = professorRepository.save(professor);
        professor.setNome("João");

        mockMvc.perform(put("/api/professor/" + professor.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(professor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void deveDeletarProfessor() throws Exception {
        professor = professorRepository.save(professor);

        mockMvc.perform(delete("/api/professor/" + professor.getId()))
                .andExpect(status().isNoContent());
    }


}
