package com.example.demo.servico;

import com.example.demo.modelo.Aluno;
import com.example.demo.modelo.Materia;
import com.example.demo.modelo.Matricula;
import com.example.demo.repository.MatriculaRepository;
import com.example.demo.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatriculaService {
    private final MatriculaRepository matriculaRepository;
    private final AlunoService alunoService;
    private final MateriaService materiaService;

    @Transactional
    public Matricula matricular(Long alunoId, Long materiaId) {
        Aluno aluno = alunoService.findById(alunoId);
        Materia materia = materiaService.findById(materiaId);

        Matricula matricula = Matricula.builder()
                .aluno(aluno)
                .materia(materia)
                .build();


        aluno.getMatriculas().add(matricula);
        materia.getMatriculas().add(matricula);

        return matriculaRepository.save(matricula);
    }
    public Matricula buscar(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Matricula não encontrada"));
    }
}
