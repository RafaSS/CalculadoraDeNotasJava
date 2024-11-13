package com.example.demo.servico;

import com.example.demo.modelo.Aluno;
import com.example.demo.modelo.Materia;
import com.example.demo.modelo.Matricula;
import com.example.demo.repository.MatriculaRepository;
import com.example.demo.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
@Slf4j
@Service
@RequiredArgsConstructor
public class MatriculaService {
    private final MatriculaRepository matriculaRepository;
    private final AlunoService alunoService;
    private final MateriaService materiaService;

    @Transactional
    public Matricula matricular(Matricula matricula) {

        Aluno aluno = alunoService.findById(matricula.getAluno().getId());

        Materia materia = materiaService.findById(matricula.getMateria().getId());



        alunoService.update(aluno.getId(), aluno);
        materiaService.atualizar(materia.getId(), materia);

        return matriculaRepository.save(matricula);
    }

    public Matricula buscar(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Matricula não encontrada"));
    }

    public Matricula atualizar(Long id, Matricula matricula) {
        Aluno aluno = alunoService.findById(matricula.getAluno().getId());
        log.debug("Aluno: {}", aluno);
        Materia materia = materiaService.findById(matricula.getMateria().getId());

        if(!matriculaRepository.existsById(id)) {
            throw new BusinessException("Matricula não encontrada");
        }

        Matricula matricular = Matricula.builder()
                .id(id)
                .aluno(aluno)
                .materia(materia)
                .build();
        return matriculaRepository.save(matricular);

    }

    public void deletar(Long id) {
        Matricula matricula = buscar(id);
        try {
            matriculaRepository.delete(matricula);
        } catch (Exception e) {
            throw new BusinessException("Não é possível excluir a matricula");
        }
    }
}
