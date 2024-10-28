// src/main/java/com/example/demo/controlador/MatriculaController.java
package com.example.demo.controller;

import com.example.demo.modelo.Matricula;
import com.example.demo.servico.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matricular")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaService matriculaService;

    @PostMapping
    public ResponseEntity<Matricula> matricular(
            @RequestParam Long alunoId,
            @RequestParam Long materiaId) {
        return ResponseEntity.ok(matriculaService.matricular(alunoId, materiaId));
    }
}
