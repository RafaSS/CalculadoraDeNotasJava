package com.example.demo.controller;

import com.example.demo.servico.MatriculaService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.repository.MatriculaRepository;
import com.example.demo.repository.MateriaRepository;
import com.example.demo.repository.AlunoRepository;
import com.example.demo.repository.ProfessorRepository;
import com.example.demo.modelo.Matricula;
import com.example.demo.modelo.Materia;
import com.example.demo.modelo.Aluno;
import com.example.demo.modelo.Professor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MatriculaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    private Aluno aluno;

    private Materia materia;
    @Autowired
    private MatriculaService matriculaService;

    @BeforeEach
    void setup() {
        materiaRepository.deleteAll();
        professorRepository.deleteAll();
        alunoRepository.deleteAll();

        aluno = new Aluno(1L, "Joao", null);
        aluno = alunoRepository.save(aluno);
        Professor professor = new Professor(null, "Maria", null);
        professor = professorRepository.save(professor);
        materia = new Materia(1L, "Matematica", professor, null);
        materia = materiaRepository.save(materia);
    }

    @Test
    void deveCriarMatricula() throws Exception {

        Matricula matricula = new Matricula(null, aluno, materia);
        String matriculaJson = objectMapper.writeValueAsString(matricula);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/matricula")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(matriculaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.aluno.nome").value("Joao"))
                .andExpect(jsonPath("$.materia.nome").value("Matematica"));
    }

    @Test
    void deveBuscarMatricula() throws Exception {
        Matricula matricula = new Matricula(null, aluno, materia);
        matriculaRepository.save(matricula);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/matricula/" + matricula.getId()))
                .andExpect(status().isOk());

        System.out.println(matriculaService.buscar(matricula.getId()) + "matriculaddd");

    }

    @Test
    void deveRetornarNotFoundAoBuscarMatriculaInexistente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/matricula/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornarBadRequestAoCriarMatriculaComAlunoInexistente() throws Exception {
        Aluno alunoInexistente = new Aluno(99L, "Inexistente", null);
        Matricula matricula = new Matricula(1L, alunoInexistente, materia);
        String matriculaJson = objectMapper.writeValueAsString(matricula);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/matricula")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(matriculaJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarBadRequestAoCriarMatriculaComMateriaInexistente() throws Exception {
        Materia materiaInexistente = new Materia(99L, "Inexistente", null, null);
        Matricula matricula = new Matricula(1L, aluno, materiaInexistente);
        String matriculaJson = objectMapper.writeValueAsString(matricula);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/matricula")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(matriculaJson))
                .andExpect(status().isBadRequest());
    }

}
