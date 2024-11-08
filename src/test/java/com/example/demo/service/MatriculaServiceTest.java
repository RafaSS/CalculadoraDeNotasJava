package com.example.demo.service;

import com.example.demo.exception.BusinessException;
import com.example.demo.modelo.Aluno;
import com.example.demo.modelo.Materia;
import com.example.demo.modelo.Matricula;
import com.example.demo.repository.AlunoRepository;
import com.example.demo.repository.MateriaRepository;
import com.example.demo.repository.MatriculaRepository;
import com.example.demo.servico.AlunoService;
import com.example.demo.servico.MateriaService;
import com.example.demo.servico.MatriculaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatriculaServiceTest {

    @Mock
    private MatriculaRepository matriculaRepository;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private MateriaRepository  materiaRepository;

    @Mock
    private AlunoService alunoService;

    @Mock
    private MateriaService materiaService;

    @InjectMocks
    private MatriculaService matriculaService;

    private Matricula matricula;
    private Aluno aluno;
    private Materia materia;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        aluno = new Aluno(1L, "João", null);
        materia = new Materia(1L, "Matemática", null, null);
        matricula = new Matricula(1L, aluno, materia);

        aluno.setMatriculas(Set.of(matricula));
        materia.setMatriculas(Set.of(matricula));
    }

    @Test
    void matricularComSucesso() {
        when(alunoService.findById(any())).thenReturn(aluno);
        when(materiaService.findById(materia.getId())).thenReturn(materia);
        when(matriculaRepository.save(any(Matricula.class))).thenReturn(matricula);
        System.out.println("🫡🫡"+ matricula.getMateria());
        Matricula matriculaSalva = matriculaService.matricular(matricula);

        assertEquals(matricula, matriculaSalva);
        verify(alunoService).findById(aluno.getId());
        verify(materiaService).findById(materia.getId());
//        verify(matriculaRepository).save(matricula);
    }

    @Test
    void buscarMatriculaPorId() {
        when(matriculaRepository.findById(matricula.getId())).thenReturn(Optional.of(matricula));

        Matricula matriculaEncontrada = matriculaService.buscar(matricula.getId());

        assertEquals(matricula, matriculaEncontrada);
        verify(matriculaRepository).findById(matricula.getId());
    }

    @Test
    void naoEncontrarMatriculaPorId() {
        when(matriculaRepository.findById(matricula.getId())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> matriculaService.buscar(matricula.getId()));
        verify(matriculaRepository).findById(matricula.getId());
    }

    @Test
    void atualizarMatricula() {
        when(matriculaRepository.existsById(matricula.getId())).thenReturn(true);
        when(alunoService.findById(aluno.getId())).thenReturn(aluno);
        when(materiaService.findById(materia.getId())).thenReturn(materia);
        when(matriculaRepository.save(any(Matricula.class))).thenReturn(matricula);

        Matricula matriculaAtualizada = matriculaService.atualizar(matricula.getId(), matricula);

        assertEquals(matricula, matriculaAtualizada);
        verify(matriculaRepository).existsById(matricula.getId());
        verify(alunoService).findById(aluno.getId());
        verify(materiaService).findById(materia.getId());
        verify(matriculaRepository).save(matricula);
    }

    @Test
    void deletarMatricula() {
        when(matriculaRepository.findById(matricula.getId())).thenReturn(Optional.of(matricula));

        matriculaService.deletar(matricula.getId());

        verify(matriculaRepository).delete(matricula);
    }
}