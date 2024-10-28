package com.example.demo.servico;

import com.example.demo.modelo.Nota;
import com.example.demo.repository.NotaRepository;
import com.example.demo.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotaService {
    private static final double NOTA_MINIMA_APROVACAO = 6.0;

    private final NotaRepository notaRepository;
    private final MatriculaService matriculaService;
    private final ProfessorService professorService;

    public Nota lancarNota(Long professorId, Long matriculaId, Double valor) {
        var professor = professorService.buscar(professorId);
        var matricula = matriculaService.buscar(matriculaId);

        if (!matricula.getMateria().getProfessor().equals(professor)) {
            throw new BusinessException("Professor não autorizado para esta matéria");
        }

        var nota = Nota.builder()
                .matricula(matricula)
                .valor(valor)
                .dataLancamento(LocalDateTime.now())
                .build();

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
