package com.example.demo.controller;

import com.example.demo.modelo.*;
import com.example.demo.repository.*;
import com.example.demo.servico.NotaService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private Aluno aluno = new Aluno();

    private Matricula matricula;

    private Materia materia;
    @Autowired
    private NotaService notaService;
    @Autowired
    private MatriculaRepository matriculaRepository;

    @BeforeEach
    void setup () {
        notaRepository.deleteAll();
        matriculaRepository.deleteAll();
        materiaRepository.deleteAll();
        professorRepository.deleteAll();
        alunoRepository.deleteAll();


        Professor professor = new Professor(null, "Claudia", null);
        materia = new Materia(null, "Matematica", professor, null);
        professor.setMaterias(Set.of(materia));
        professorRepository.save(professor);

        aluno = new Aluno(null, "Maria", null);

        matricula = new Matricula(null, aluno, materia);

        aluno.setMatriculas(Set.of(matricula));
        materia.setMatriculas(Set.of(matricula));

        alunoRepository.save(aluno);
        materiaRepository.save(materia);
        matriculaRepository.save(matricula);




    }

    @Test
    void deveCriarNota() throws Exception {
        Nota nota = new Nota(null, matricula, 10.0);
        String notaJson = objectMapper.writeValueAsString(nota);

        mockMvc.perform(post("/api/nota/lancar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(notaJson))
                .andExpect(status().isOk());


    }



    @Test
    void deveCalcularMedia() throws Exception {
        Nota nota = new Nota(null, matricula, 10.0);
        notaRepository.save(nota);

        Nota nota2 = new Nota(null, matricula, 5.0);
        notaRepository.save(nota2);

        mockMvc.perform(get("/api/nota/media/" + matricula.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(7.5));
    }

    @Test
    void deveVerificarAprovacao() throws Exception {
        Nota nota = new Nota(null, matricula, 10.0);
        notaRepository.save(nota);

        Nota nota2 = new Nota(null, matricula, 5.0);
        notaRepository.save(nota2);

        mockMvc.perform(get("/api/nota/aprovacao/" + matricula.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void deveVerificarReprovacao() throws Exception {
        Nota nota = new Nota(null, matricula, 4.0);
        notaRepository.save(nota);

        Nota nota2 = new Nota(null, matricula, 5.0);
        notaRepository.save(nota2);

        mockMvc.perform(get("/api/nota/aprovacao/" + matricula.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    void deveRetornarBadRequest() throws Exception {
        mockMvc.perform(get("/api/nota/media/100"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarBadRequestNotaInvalida() throws Exception {
        Nota nota = new Nota(null, matricula, 11.0);


        mockMvc.perform(post("/api/nota/lancar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nota)))
                .andExpect(status().isBadRequest());


    }

    @Test
    void deveBuscarNota() throws Exception {
        Nota nota = new Nota(null, matricula, 10.0);
        notaRepository.save(nota);

     mockMvc.perform(get("/api/nota/" + matricula.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void deveDeletarNota() throws Exception {
        Nota nota = new Nota(null, matricula, 10.0);
        notaRepository.save(nota);

        mockMvc.perform(delete("/api/nota/deletar/" + matricula.getId() + "/" + nota.getId()))
                .andExpect(status().isOk());
    }
}
