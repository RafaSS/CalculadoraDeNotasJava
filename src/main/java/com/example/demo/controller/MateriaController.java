package com.example.demo.controller;

import com.example.demo.exception.BusinessException;
import com.example.demo.modelo.Materia;
import com.example.demo.servico.MateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/materia")
@RequiredArgsConstructor


public class MateriaController {

        private final MateriaService materiaService;

        @PostMapping
        public ResponseEntity<Materia> criar(@RequestBody Materia materia) {
            try {
                return ResponseEntity.ok(materiaService.criar(materia));
            } catch (BusinessException e) {
                return ResponseEntity.status(400).build();
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<Materia> buscar(@PathVariable Long id) {
            try {
                return ResponseEntity.ok(materiaService.findById(id));
            } catch (BusinessException e) {
                return ResponseEntity.status(404).build();
            }
        }
}
