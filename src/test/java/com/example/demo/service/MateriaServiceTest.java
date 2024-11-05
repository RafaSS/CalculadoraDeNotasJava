package com.example.demo.service;

import com.example.demo.exception.BusinessException;
import com.example.demo.modelo.Materia;
import com.example.demo.modelo.Professor;
import com.example.demo.repository.MateriaRepository;
import com.example.demo.repository.ProfessorRepository;
import com.example.demo.servico.MateriaService;
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

class MateriaServiceTest {

    @Mock
    private MateriaRepository materiaRepository;

    @Mock
    private ProfessorRepository professorRepository;

    @InjectMocks
    private MateriaService materiaService;

    @InjectMocks
    private ProfessorService professorService;

    private Materia materia;
    private Professor professor;


    @BeforeEach
    void setup() {
        professor = new Professor(1L, "pedro", null);
        materia = new Materia(null, "Matemática", professor,null);


        professor.setMaterias(Set.of(materia));
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarMateriaComSucesso() {
        Materia materia = new Materia(null, "Matemática", new Professor(1L,"pedro",null),null);
        when(materiaRepository.save(materia)).thenReturn(materia);
        when(professorRepository.save(professor)).thenReturn(professor);
        when(materiaRepository.findById(materia.getId())).thenReturn(Optional.empty());

        Professor savedProfessor = professorService.save(professor);
        Materia savedMateria = materiaService.criar(materia);

        assertEquals(materia, savedMateria);
        verify(materiaRepository).save(materia);
        verifyNoMoreInteractions(materiaRepository);
    }

    @Test
    void criarMateriaComNomeNulo() {
        Materia materia = new Materia(null, null, null,null);
        assertThrows(BusinessException.class, () -> materiaService.criar(materia));
        verifyNoInteractions(materiaRepository);
    }

    @Test
    void criarMateriaComNomeVazio() {
        Materia materia = new Materia(null, "", null,null);
        assertThrows(BusinessException.class, () -> materiaService.criar(materia));
        verifyNoInteractions(materiaRepository);
    }

    @Test
    void criarMateriaComProfessorNulo() {
        Materia materia = new Materia(null, "Matemática", null,null);
        materia.setProfessor(null);
        assertThrows(BusinessException.class, () -> materiaService.criar(materia));
        verifyNoInteractions(materiaRepository);
    }

    @Test
    void buscarMateriaPorId() {
        Materia materia = new Materia(1L, "Matemática", null,null);
        when(materiaRepository.findById(1L)).thenReturn(Optional.of(materia));

        Materia materiaEncontrada = materiaService.findById(1L);

        assertEquals(materia, materiaEncontrada);
        verify(materiaRepository).findById(1L);
        verifyNoMoreInteractions(materiaRepository);
    }
}
