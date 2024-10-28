package com.example.demo.servico;

import com.example.demo.repository.AlunoRepository;
import com.example.demo.exception.BusinessException;
import com.example.demo.modelo.Aluno;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AlunoService {
    private final AlunoRepository alunoRepository;

    public Aluno criar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public Aluno buscar(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Aluno não encontrado"));
    }
}
