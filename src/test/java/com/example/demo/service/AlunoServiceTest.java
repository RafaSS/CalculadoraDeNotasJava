package com.example.demo.service;

import com.example.demo.exception.BusinessException;

import com.example.demo.modelo.Aluno;
import com.example.demo.repository.AlunoRepository;
import com.example.demo.service.AlunoServiceTest;
import com.example.demo.servico.AlunoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarAlunoComSucesso() {
        Aluno aluno = new Aluno(null, "Dr. João", null);
        when(alunoRepository.save(aluno)).thenReturn(aluno);
        when(alunoRepository.findById(aluno.getId())).thenReturn(Optional.empty());

        Aluno savedAluno = alunoService.create(aluno);

        assertEquals(aluno, savedAluno);
        verify(alunoRepository).save(aluno);
        verifyNoMoreInteractions(alunoRepository);
    }

    @Test
    void criarAlunoComNomeNulo() {
        Aluno aluno = new Aluno(null, null, null);
        assertThrows(BusinessException.class, () -> alunoService.create(aluno));
        verifyNoInteractions(alunoRepository);
    }

    @Test
    void criarAlunoComNomeVazio() {
        Aluno aluno = new Aluno(null, "", null);
        assertThrows(BusinessException.class, () -> alunoService.create(aluno));
        verifyNoInteractions(alunoRepository);
    }
}
