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
        if (materia.getProfessor() == null ) {
            throw new BusinessException("Professor é obrigatório para criar uma matéria.");
        }
        System.out.println(professorService+"id do professor");
        Professor professor = professorService.buscar(materia.getProfessor().getId());
        if (professor == null) {
            throw new BusinessException("Professor não encontrado.");
        }
        professor.getMaterias().add(materia);
        professorService.atualizar(professor.getId(), professor);
        return materiaRepository.save(materia);
    }

    public Materia findById(Long id) {

        return materiaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Materia não encontrada"));
    }

    public Materia atualizar(Long id, Materia materia) {
        Materia materiaSalva = findById(id);
        materia.setId(materiaSalva.getId());
        return materiaRepository.save(materia);
    }

    public void deletar(Long id) {
        Materia materia = findById(id);
        materiaRepository.delete(materia);
    }


}
