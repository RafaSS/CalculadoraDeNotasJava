package com.example.demo.controller;

import com.example.demo.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluno")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @PostMapping("/criar")
    public ResponseEntity<String> criarAluno(@RequestParam String nome) {
        notaService.criarAluno(nome);
        return new ResponseEntity<>("Aluno " + nome + " criado com sucesso!", HttpStatus.CREATED);
    }

    @PostMapping("/nota")
    public ResponseEntity<String> adicionarNota(@RequestParam String nome, @RequestParam double nota) {
        try {
            notaService.adicionarNota(nome, nota);
            return new ResponseEntity<>("Nota adicionada com sucesso para o aluno " + nome, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/media")
    public ResponseEntity<String> calcularMedia(@RequestParam String nome) {
        try {
            double media = notaService.calcularMedia(nome);
            return new ResponseEntity<>("A média do aluno " + nome + " é " + media, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> verificarAprovacao(@RequestParam String nome) {
        try {
            boolean aprovado = notaService.foiAprovado(nome);
            String mensagem = aprovado ? "Aluno " + nome + " aprovado!" : "Aluno " + nome + " reprovado!";
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
