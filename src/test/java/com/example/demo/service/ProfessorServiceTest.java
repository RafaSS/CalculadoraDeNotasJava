package com.example.demo.service;

import com.example.demo.exception.BusinessException;
import com.example.demo.modelo.Materia;
import com.example.demo.modelo.Professor;
import com.example.demo.repository.ProfessorRepository;
import com.example.demo.servico.ProfessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfessorServiceTest {

    @Mock
    private ProfessorRepository professorRepository;

    @InjectMocks
    private ProfessorService professorService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void criarProfessorComSucesso() {
        Professor professor = new Professor(null, "Dr. João", null);
        when(professorRepository.save(professor)).thenReturn(professor);
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.empty());

        Professor savedProfessor = professorService.save(professor);

        assertEquals(professor, savedProfessor);
        verify(professorRepository).save(professor);
        verifyNoMoreInteractions(professorRepository);
    }

    @Test
    void criarProfessorComNomeNulo() {
        Professor professor = new Professor(null, null, null);
        assertThrows(BusinessException.class, () -> professorService.save(professor));
        verifyNoInteractions(professorRepository);
    }

    @Test
    void criarProfessorComNomeVazio() {
        Professor professor = new Professor(null, "", null);
        assertThrows(BusinessException.class, () -> professorService.save(professor));
        verifyNoInteractions(professorRepository);
    }

    @Test
    void criarProfessorComMateria() {
        Materia materia = new Materia(null, "Matemática", null,null);
        Professor professor = new Professor(null, "Dr. João", Set.of(materia));
        when(professorRepository.save(professor)).thenReturn(professor);
        when(professorRepository.findById(professor.getId())).thenReturn(Optional.empty());

        Professor savedProfessor = professorService.save(professor);

        assertEquals(professor, savedProfessor);
        verify(professorRepository).save(professor);
        verifyNoMoreInteractions(professorRepository);
    }
}