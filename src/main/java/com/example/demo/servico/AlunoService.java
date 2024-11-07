package com.example.demo.servico;

import com.example.demo.repository.AlunoRepository;
import com.example.demo.exception.BusinessException;
import com.example.demo.modelo.Aluno;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor

@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;

    public Aluno create(Aluno aluno) {
        if(aluno.getNome() == null || aluno.getNome().isEmpty()) {
            throw new BusinessException("Nome do aluno é obrigatório");
        }
        return alunoRepository.save(aluno);
    }

    public Aluno findById(Long id) {

        return alunoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Aluno não encontrado"));
    }

    public Aluno update(Long id, Aluno aluno) {
        Aluno alunoSalvo = findById(id);
        aluno.setId(alunoSalvo.getId());
       try {
              return alunoRepository.save(aluno);
         } catch (Exception e) {
              throw new BusinessException("Não foi possível atualizar o aluno");
       }
    }

    public void delete(Long id) {
        Aluno aluno = findById(id);
        try {
            alunoRepository.delete(aluno);
        } catch (Exception e) {
            throw new BusinessException("Não é possível excluir o aluno");
        }
    }
}
