package com.example.demo.service;

import com.example.demo.exception.BusinessException;
import com.example.demo.modelo.Materia;
import com.example.demo.modelo.Professor;
import com.example.demo.repository.MateriaRepository;
import com.example.demo.repository.ProfessorRepository;
import com.example.demo.servico.MateriaService;
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


class MateriaServiceTest {

    @Mock
    private MateriaRepository materiaRepository;

    @Mock
    private ProfessorRepository professorRepository;

    @InjectMocks
    private MateriaService materiaService;

    private Materia materia;
    private Professor professor;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        professor = new Professor(1L, "pedro", null);
        materia = new Materia(null, "Matemática", professor, null);

        professor.setMaterias(Set.of(materia));
    }

    @Test
    void criarMateriaComSucesso() {
        when(professorRepository.findById(materia.getProfessor().getId())).thenReturn(Optional.of(professor));
        when(materiaRepository.save(materia)).thenReturn(materia);
        when(materiaRepository.findById(anyLong())).thenReturn(Optional.empty());


        Materia materiaSalva = materiaService.criar(materia);

        assertEquals(materia, materiaSalva);
        verify(materiaRepository).save(materia);
        verifyNoMoreInteractions(materiaRepository);
        verify(professorRepository).findById(professor.getId());
    }

    @Test
    void criarMateriaSemProfessor() {
        materia.setProfessor(null);
        assertThrows(BusinessException.class, () -> materiaService.criar(materia));
        verifyNoInteractions(materiaRepository);
    }

    @Test
    void buscarMateriaPorId() {
        when(materiaRepository.findById(materia.getId())).thenReturn(Optional.of(materia));
        Materia materiaSalva = materiaService.findById(materia.getId());
        assertEquals(materia, materiaSalva);
        verify(materiaRepository).findById(materia.getId());
    }

    @Test
    void naoEncontrarMateriaPorId() {
        when(materiaRepository.findById(materia.getId())).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> materiaService.findById(materia.getId()));
        verify(materiaRepository).findById(materia.getId());
    }

//    @Test
//    void atualizarMateria() {
//        Materia materiaSalva = new Materia(1L, "Matemática", professor, null);
//        when(materiaRepository.findById(materia.getId())).thenReturn(Optional.of(materiaSalva));
//        when(materiaRepository.save(materia)).thenReturn(materia);
//
//        Materia materiaAtualizada = materiaService.atualizar(materia.getId(), materia);
//
//        assertEquals(materia, materiaAtualizada);
//        verify(materiaRepository).findById(materia.getId());
//        verify(materiaRepository).save(materia);
//    }

    @Test
    void deletarMateria() {
        when(materiaRepository.findById(materia.getId())).thenReturn(Optional.of(materia));
        materiaService.deletar(materia.getId());
        verify(materiaRepository).delete(materia);
    }


}