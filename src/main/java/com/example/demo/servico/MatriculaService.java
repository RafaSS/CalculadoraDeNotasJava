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
    public Matricula matricular(Matricula matricula) {
        Aluno aluno = alunoService.findById(matricula.getAluno().getId());
        Materia materia = materiaService.findById(matricula.getMateria().getId());
        System.out.println(aluno+"aluno1::");

        Matricula matricular = Matricula.builder()
                .aluno(aluno)
                .materia(materia)
                .build();


        aluno.getMatriculas().add(matricular);
        materia.getMatriculas().add(matricular);

        return matriculaRepository.save(matricular);
    }

    public Matricula buscar(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Matricula não encontrada"));
    }

    public Matricula atualizar(Long id, Matricula matricula) {
        Aluno aluno = alunoService.findById(matricula.getAluno().getId());
        System.out.println(aluno+"aluno::");
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
        matriculaRepository.delete(matricula);
    }
}
