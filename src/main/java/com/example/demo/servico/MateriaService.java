package com.example.demo.servico;


import com.example.demo.exception.BusinessException;

import com.example.demo.modelo.Materia;
import com.example.demo.modelo.Professor;
import com.example.demo.repository.MateriaRepository;
import com.example.demo.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor

public class MateriaService {
    private final MateriaRepository materiaRepository;
    private final ProfessorRepository professorRepository;

    public Materia criar(Materia materia) {
        if (materia.getProfessor() == null) {
            throw new BusinessException("Professor é obrigatório para criar uma matéria.");
        }
        System.out.println(materia.getProfessor().getId() + "id do professor");
        Professor professor = professorRepository.findById(materia.getProfessor().getId())
                .orElseThrow(() -> new BusinessException("Professor não encontrado."));
        if (professor == null) {
            throw new BusinessException("Professor não encontrado.");
        }

        System.out.println("☻" +materia);
        return materiaRepository.save(materia);
    }

    public Materia findById(Long id) {

        return materiaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Materia não encontrada"));
    }

    public Materia atualizar(Long id, Materia materia) {
        Materia materiaSalva = findById(id);
        materia.setId(materiaSalva.getId());
        try {
            return materiaRepository.save(materia);
        } catch (Exception e) {
            throw new BusinessException("Não foi possível atualizar a matéria");
        }
    }

    public void deletar(Long id) {
        Materia materia = findById(id);
        try {
            materiaRepository.delete(materia);
        } catch (Exception e) {
            throw new BusinessException("Não é possível excluir a matéria");
        }
    }


}
