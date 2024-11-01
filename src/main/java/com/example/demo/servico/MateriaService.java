package com.example.demo.servico;


import com.example.demo.exception.BusinessException;

import com.example.demo.modelo.Materia;
import com.example.demo.modelo.Professor;
import com.example.demo.repository.MateriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor

public class MateriaService {
    private final MateriaRepository materiaRepository;
    private final ProfessorService professorService;

    public Materia criar(Materia materia) {

       Professor professor = professorService.buscar(materia.getProfessor().getId());
        if (materia.getProfessor() == null || materia.getProfessor().getId() == null) {
            throw new BusinessException("Professor é obrigatório para criar uma matéria.");
        }
        materia.setProfessor(professor);
        professor.getMaterias().add(materia);
        return materiaRepository.save(materia);
    }

    public Materia findById(Long id) {

        return materiaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Materia não encontrada"));
    }


}
