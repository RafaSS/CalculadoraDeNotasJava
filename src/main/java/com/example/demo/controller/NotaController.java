package com.example.demo.controller;

import com.example.demo.exception.BusinessException;
import com.example.demo.modelo.Matricula;
import com.example.demo.modelo.Nota;
import com.example.demo.servico.NotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/aprovacao/{matriculaId}")
    public ResponseEntity<Boolean> verificarAprovacao(@PathVariable Long matriculaId) {
        try{
            return ResponseEntity.ok(notaService.verificarAprovacao(matriculaId));
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

    @GetMapping("/{matriculaId}")
    public ResponseEntity<List<Nota>> buscarNota(@PathVariable Long matriculaId) {
        try {
            return ResponseEntity.ok(notaService.buscarNota(matriculaId));
        } catch (BusinessException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/atualizar/{matriculaId}")
    public ResponseEntity<Nota> atualizarNota(@PathVariable Long matriculaId, @RequestBody Nota nota) {
        try {
            return ResponseEntity.ok(notaService.atualizarNota(matriculaId, nota));
        } catch (BusinessException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/deletar/{matriculaId}/{notaId}")
    public ResponseEntity<Nota> deletarNota(@PathVariable Long matriculaId, @PathVariable Long notaId) {
        try {
            notaService.deletarNota(matriculaId, notaId);
            return ResponseEntity.ok().build();
        } catch (BusinessException e) {
            return ResponseEntity.status(404).build();
        }
    }
}
