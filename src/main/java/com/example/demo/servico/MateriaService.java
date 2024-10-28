package com.example.demo.servico;


import com.example.demo.exception.BusinessException;

import com.example.demo.modelo.Materia;
import com.example.demo.repository.MateriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor

public class MateriaService {
    private final MateriaRepository materiaRepository;

    public Materia criar(Materia materia) {
        return materiaRepository.save(materia);
    }

    public Materia buscar(Long id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Materia não encontrada"));
    }


}
