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

    public Professor save(Professor professor) {
        if(professor.getNome() == null || professor.getNome().isEmpty()) {
            throw new BusinessException("Nome do professor é obrigatório");
        }
        return professorRepository.save(professor);
    }

    public Professor buscar(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Professor não encontrado"));
    }

    public Professor atualizar(Long id, Professor professor) {
        Professor professorSalvo = buscar(id);
        professor.setId(professorSalvo.getId());
        return professorRepository.save(professor);
    }

    public void deletar(Long id) {
        Professor professor = buscar(id);
        professorRepository.delete(professor);
    }
}
