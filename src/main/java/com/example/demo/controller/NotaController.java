package com.example.demo.controller;

import com.example.demo.exception.BusinessException;
import com.example.demo.modelo.Matricula;
import com.example.demo.modelo.Nota;
import com.example.demo.servico.NotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nota")
@RequiredArgsConstructor
public class NotaController {

    private final NotaService notaService;

    @PostMapping("/lancar")
    public ResponseEntity<Nota> lancarNota(
            @RequestBody Nota nota) {

        try {
            return ResponseEntity.ok(notaService.lancarNota(nota));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/media/{matriculaId}")
    public ResponseEntity<Double> calcularMedia(@PathVariable Long matriculaId) {
        try{
            return ResponseEntity.ok(notaService.calcularMedia(matriculaId));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
