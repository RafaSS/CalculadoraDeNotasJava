package com.example.demo.service;

import com.example.demo.models.Aluno;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotaService {

    private List<Aluno> alunos = new ArrayList<>();

    public Aluno criarAluno(String nome) {
        Aluno aluno = new Aluno(nome);
        alunos.add(aluno);
        return aluno;
    }

    public Aluno buscarAluno(String nome) {
        return alunos.stream()
                .filter(aluno -> aluno.getNome().equals(nome))
                .findFirst()
                .orElse(null);
    }

    public void adicionarNota(String nome, double nota) {
        Aluno aluno = buscarAluno(nome);
        if (aluno != null) {
            if (nota < 0 || nota > 10) {
                throw new IllegalArgumentException("Nota deve estar entre 0 e 10");
            }
            aluno.adicionarNota(nota);
        }
    }

    public double calcularMedia(String nome) {
        Aluno aluno = buscarAluno(nome);
        if (aluno != null) {
            return aluno.calcularMedia();
        }
        throw new IllegalArgumentException("Aluno não encontrado");
    }

    public boolean foiAprovado(String nome) {
        Aluno aluno = buscarAluno(nome);
        if (aluno != null) {
            return aluno.foiAprovado();
        }
        throw new IllegalArgumentException("Aluno não encontrado");
    }
}
