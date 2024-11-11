package com.example.demo.servico;

import com.example.demo.modelo.Matricula;
import com.example.demo.modelo.Nota;
import com.example.demo.modelo.Professor;
import com.example.demo.repository.NotaRepository;
import com.example.demo.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotaService {
    private static final double NOTA_MINIMA_APROVACAO = 6.0;

    private final NotaRepository notaRepository;
    private final MatriculaService matriculaService;
    private final ProfessorService professorService;

    public Nota lancarNota(Nota nota) {
        if (nota.getValor()==null) {
            throw new BusinessException("Nota é obrigatória");
        }
        if (nota.getValor() < 0 || nota.getValor() > 10) {
            throw new BusinessException("Nota deve ser entre 0 e 10");
        }
        if (nota.getMatricula() == null || nota.getMatricula().getId() == null) {
            throw new BusinessException("Matricula é obrigatória");
        }
        Matricula matricula = matriculaService.buscar(nota.getMatricula().getId());

        var professor = professorService.buscar(matricula.getMateria().getProfessor().getId());

        if (!professor.getId().equals(nota.getMatricula().getMateria().getProfessor().getId())) {
            throw new BusinessException("Professor não é responsável pela matéria da matrícula");
        }

//        Nota notaSalva = Nota.builder().matricula(nota.getMatricula()).valor(nota.getValor()).build();



        return notaRepository.save(nota);
    }

    public Double calcularMedia(Long matriculaId) {
        var notas = notaRepository.findByMatriculaId(matriculaId);

        if (notas.isEmpty()) {
            throw new BusinessException("Matricula não possui notas");
        }

        return notas.stream()
                .mapToDouble(Nota::getValor)
                .average()
                .orElse(0.0);
    }



    public boolean verificarAprovacao(Long matriculaId) {
        return calcularMedia(matriculaId) >= NOTA_MINIMA_APROVACAO;
    }

    public List<Nota> buscarNota(Long matriculaId) {
        try {
            return notaRepository.findByMatriculaId(matriculaId);
        } catch (BusinessException e) {
            throw new BusinessException("Nota não encontrada");
        }


    }

    public Nota atualizarNota(Long matriculaId, Nota nota) {
        var notas = buscarNota(matriculaId);
        var notaSalva = notas.stream()
                .filter(n -> n.getId().equals(nota.getId()))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Nota não encontrada"));

        notaSalva.setValor(nota.getValor());
        return notaRepository.save(notaSalva);

    }

    public void deletarNota(Long matriculaId, Long notaId) {
        var notas = buscarNota(matriculaId);
        var nota = notas.stream()
                .filter(n -> n.getId().equals(notaId))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Nota não encontrada"));

        notaRepository.delete(nota);
    }
}
