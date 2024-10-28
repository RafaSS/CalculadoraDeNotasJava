package com.example.demo.servico;

import com.example.demo.modelo.Professor;
import com.example.demo.repository.ProfessorRepository;
import com.example.demo.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public Professor criar(Professor professor) {
        return professorRepository.save(professor);
    }

    public Professor buscar(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Professor não encontrado"));
    }
}
