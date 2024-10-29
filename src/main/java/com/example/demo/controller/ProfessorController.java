package com.example.demo.controller;

import com.example.demo.modelo.Professor;
import com.example.demo.servico.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/professor")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @PostMapping("/criar")
    public ResponseEntity<Professor> criar(@RequestBody Professor professor) {
        return ResponseEntity.ok(professorService.save(professor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(professorService.buscar(id));
    }
}
