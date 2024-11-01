//package com.example.demo.controller;
//
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import com.example.demo.repository.MatriculaRepository;
//import com.example.demo.repository.MateriaRepository;
//import com.example.demo.repository.AlunoRepository;
//import com.example.demo.repository.ProfessorRepository;
//import com.example.demo.modelo.Matricula;
//import com.example.demo.modelo.Materia;
//import com.example.demo.modelo.Aluno;
//import com.example.demo.modelo.Professor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest
//public class MatriculaControllerTest {
//
//        @Autowired
//        private MockMvc mockMvc;
//
//        @Autowired
//        private MatriculaRepository matriculaRepository;
//
//        @Autowired
//        private ObjectMapper objectMapper;
//
//        @Test
//        void deveCriarMatricula() throws Exception {
//            Professor professor = new Professor(null, "Maria", null);
//            Materia materia = new Materia(null, "Matematica", professor, null);
//            Aluno aluno = new Aluno(null, "Joao", null);
//            Matricula matricula = new Matricula(null, aluno, materia);
//            String matriculaJson = objectMapper.writeValueAsString(matricula);
//
//            mockMvc.perform(MockMvcRequestBuilders.post("/api/matricula")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(matriculaJson))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.aluno.nome").value("Joao"))
//                    .andExpect(jsonPath("$.materia.nome").value("Matematica"));
//        }
//
//        @Test
//        void deveBuscarMatricula() throws Exception {
//            Professor professor = new Professor(null, "Maria", null);
//            Materia materia = new Materia(null, "Matematica", professor, null);
//            Aluno aluno = new Aluno(null, "Joao", null);
//            Matricula matricula = new Matricula(null, aluno, materia);
//            matriculaRepository.save(matricula).wait();
//
//            mockMvc.perform(MockMvcRequestBuilders.get("/api/matricula/" + matricula.getId()))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.aluno.nome").value("Joao"))
//                    .andExpect(jsonPath("$.materia.nome").value("Matematica"));
//        }
//
//        @Test
//        void deveRetornarNotFoundAoBuscarMatriculaInexistente() throws Exception {
//            mockMvc.perform(MockMvcRequestBuilders.get("/api/matricula/1"))
//                    .andExpect(status().isNotFound());
//        }
//
//}
