package com.example.demo.controller;

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
            @RequestParam Long professorId,
            @RequestParam Long matriculaId,
            @RequestBody Double valor) {
        return ResponseEntity.ok(notaService.lancarNota(professorId, matriculaId, valor));
    }

    @GetMapping("/media/{matriculaId}")
    public ResponseEntity<Double> calcularMedia(@PathVariable Long matriculaId) {
        return ResponseEntity.ok(notaService.calcularMedia(matriculaId));
    }
}
