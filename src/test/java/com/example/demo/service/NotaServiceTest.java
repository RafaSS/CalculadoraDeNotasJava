package com.example.demo.service;

import com.example.demo.exception.BusinessException;
import com.example.demo.modelo.*;
import com.example.demo.repository.NotaRepository;
import com.example.demo.servico.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotaServiceTest {

    @Mock
    private NotaRepository notaRepository;

    @Mock
    private AlunoService alunoService;

    @Mock
    private MateriaService materiaService;

    @Mock
    private ProfessorService professorService;

    @Mock
    private MatriculaService matriculaService;

    @InjectMocks
    private NotaService notaService;

    private Nota nota;
    private Aluno aluno;
    private Materia materia;
    private Matricula matricula;
    private Professor professor;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        aluno = new Aluno(1L, "João", null);
        professor = new Professor(1L, "Pedro", null);
        materia = new Materia(1L, "Matemática", professor, null);
        matricula = new Matricula(1L, aluno, materia);

        aluno.setMatriculas(Set.of(matricula));
        materia.setMatriculas(Set.of(matricula));
        professor.setMaterias(Set.of(materia));

        nota = new Nota(1L, matricula, 9.5);
    }

    @Test
    void criarNotaComSucesso() {
        when(matriculaService.buscar(matricula.getId())).thenReturn(matricula);
        when(notaRepository.save(nota)).thenReturn(nota);
        when(professorService.buscar(professor.getId())).thenReturn(professor);
        when(notaRepository.findById(anyLong())).thenReturn(Optional.empty());

        Nota notaSalva = notaService.lancarNota(nota);

        assertEquals(nota, notaSalva);
        verify(notaRepository).save(nota);
        verifyNoMoreInteractions(notaRepository);
        verify(matriculaService).buscar(matricula.getId());
    }

    @Test
    void criarNotaComMatriculaNula() {
        nota.setMatricula(null);
        assertThrows(BusinessException.class, () -> notaService.lancarNota(nota));
        verifyNoInteractions(notaRepository);
    }

    @Test
    void criarNotaComNotaMenorQueZero() {
        nota.setValor(-1.0);
        assertThrows(BusinessException.class, () -> notaService.lancarNota(nota));
        verifyNoInteractions(notaRepository);
    }

    @Test
    void criarNotaComNotaMaiorQueDez() {
        nota.setValor(11.0);
        assertThrows(BusinessException.class, () -> notaService.lancarNota(nota));
        verifyNoInteractions(notaRepository);
    }

    @Test
    void criarNotaComNotaNula() {
        nota.setValor(null);
        assertThrows(BusinessException.class, () -> notaService.lancarNota(nota));
        verifyNoInteractions(notaRepository);
    }

    @Test
    void criarNotaComMatriculaInexistente() {
        when(matriculaService.buscar(matricula.getId())).thenThrow(new BusinessException("Matrícula não encontrada"));
        assertThrows(BusinessException.class, () -> notaService.lancarNota(nota));
        verify(matriculaService).buscar(matricula.getId());
        verifyNoInteractions(notaRepository);
    }

    @Test
    void deveCalcularMedia() {
        when(notaRepository.findByMatriculaId(matricula.getId())).thenReturn(List.of(nota));
        assertEquals(9.5, notaService.calcularMedia(matricula.getId()));
        verify(notaRepository).findByMatriculaId(matricula.getId());
    }

    @Test
    void deveVerificarAprovacao() {
        when(notaRepository.findByMatriculaId(matricula.getId())).thenReturn(List.of(nota));
        assertTrue(notaService.verificarAprovacao(matricula.getId()));
        verify(notaRepository).findByMatriculaId(matricula.getId());
    }

}

