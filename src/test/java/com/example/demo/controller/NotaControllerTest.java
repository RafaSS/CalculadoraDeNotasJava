package com.example.demo.controller;

import com.example.demo.modelo.*;
import com.example.demo.repository.*;
import com.example.demo.servico.NotaService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NotaControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private NotaRepository notaRepository;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private MateriaRepository materiaRepository;

        @Autowired
        private AlunoRepository alunoRepository;

        @Autowired
        private ProfessorRepository professorRepository;

        private Aluno aluno;

        private Matricula matricula;

        private Materia materia;
        @Autowired
        private NotaService notaService;
    @Autowired
    private MatriculaRepository matriculaRepository;

    @BeforeEach
        void setup() {
            materiaRepository.deleteAll();
            professorRepository.deleteAll();
            alunoRepository.deleteAll();

            aluno = new Aluno(null, "Maria", null);
            aluno = alunoRepository.save(aluno);
            Professor professor = new Professor(1L, "Maria", null);
            professor = professorRepository.save(professor);
            materia = new Materia(1L, "Matematica", professor, null);
            materia = materiaRepository.save(materia);
            matricula = new Matricula(1L, aluno, materia);
            matricula = matriculaRepository.save(matricula);

        }

        @Test
        void deveCriarNota() throws Exception {
            Nota nota = new Nota(1L, matricula, 10.0);
            String notaJson = objectMapper.writeValueAsString(nota);

            mockMvc.perform(post("/api/nota/lancar")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(notaJson))
                            .andExpect(status().isOk());

        }

//        @Test
//        void deveBuscarNota() throws Exception {
//            Nota nota = new Nota(null, matricula, 10.0);
//            nota = notaRepository.save(nota);
//
//            mockMvc.perform(get("/api/nota/" + nota.getId()))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.id").value(nota.getId()))
//                    .andExpect(jsonPath("$.nota").value(nota.getValor()));
//        }

//        @Test
//        void deveRetornar404QuandoNotaNaoEncontrada() throws Exception {
//            mockMvc.perform(get("/api/nota/1"))
//                    .andExpect(status().isNotFound());
//        }

//        @Test
//        void deveRetornar400QuandoNotaInvalida() throws Exception {
//            Nota nota = new Nota(null,matricula, 11.0);
//            nota = notaRepository.save(nota);
//
//            mockMvc.perform(get("/api/nota/" + nota.getId()))
//                    .andExpect(status().isNotFound());
//        }
}
