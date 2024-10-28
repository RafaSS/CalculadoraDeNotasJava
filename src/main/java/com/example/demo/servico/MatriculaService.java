package com.example.demo.servico;

import com.example.demo.modelo.Matricula;
import com.example.demo.repository.MatriculaRepository;
import com.example.demo.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatriculaService {
    private final MatriculaRepository matriculaRepository;
    private final AlunoService alunoService;
    private final MateriaService materiaService;

    public Matricula matricular(Long alunoId, Long materiaId) {
        var aluno = alunoService.buscar(alunoId);
        var materia = materiaService.buscar(materiaId);

        var matricula = Matricula.builder()
                .aluno(aluno)
                .materia(materia)
                .build();

        return matriculaRepository.save(matricula);
    }

    public Matricula buscar(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Matricula não encontrada"));
    }
}
