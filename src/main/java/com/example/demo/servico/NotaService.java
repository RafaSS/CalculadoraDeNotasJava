package com.example.demo.servico;

import com.example.demo.modelo.Matricula;
import com.example.demo.modelo.Nota;
import com.example.demo.modelo.Professor;
import com.example.demo.repository.NotaRepository;
import com.example.demo.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotaService {
    private static final double NOTA_MINIMA_APROVACAO = 6.0;

    private final NotaRepository notaRepository;
    private final MatriculaService matriculaService;
    private final ProfessorService professorService;

    public Nota lancarNota(Nota nota) {
        var matricula = matriculaService.buscar(nota.getMatricula().getId());
        System.out.println("Matricula: " + matricula);
        var professor = professorService.buscar(matricula.getMateria().getProfessor().getId());

        if (!professor.getId().equals(nota.getMatricula().getMateria().getProfessor().getId())) {
            throw new BusinessException("Professor não é responsável pela matéria da matrícula");
        }



        return notaRepository.save(nota);
    }

    public Double calcularMedia(Long matriculaId) {
        var notas = notaRepository.findByMatriculaId(matriculaId);

        return notas.stream()
                .mapToDouble(Nota::getValor)
                .average()
                .orElse(0.0);
    }

    public boolean verificarAprovacao(Long matriculaId) {
        return calcularMedia(matriculaId) >= NOTA_MINIMA_APROVACAO;
    }
}
