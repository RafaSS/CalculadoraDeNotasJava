//package com.example.demo.servico;
//
//import com.example.demo.modelo.Aluno;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class NotaServiceTest {
//
//    private final NotaService notaService = new NotaService();
//
//    @Test
//    public void adicionarNotaValida() {
//        notaService.criarAluno("João");
//        notaService.adicionarNota("João", 8.5);
//        Aluno aluno = notaService.buscarAluno("João");
//        assertEquals(8.5, aluno.getNotas().get(0));
//    }
//
//    @Test
//    public void calcularMediaCorreta() {
//        notaService.criarAluno("Maria");
//        notaService.adicionarNota("Maria", 8.0);
//        notaService.adicionarNota("Maria", 6.0);
//        double media = notaService.calcularMedia("Maria");
//        assertEquals(7.0, media, 0.01);
//    }
//
//    @Test
//    public void verificarAprovacao() {
//        notaService.criarAluno("Pedro");
//        notaService.adicionarNota("Pedro", 5.0);
//        notaService.adicionarNota("Pedro", 7.0);
//        assertTrue(notaService.foiAprovado("Pedro"));
//    }
//
//    @Test
//    public void alunoReprovado() {
//        notaService.criarAluno("Ana");
//        notaService.adicionarNota("Ana", 4.0);
//        assertFalse(notaService.foiAprovado("Ana"));
//    }
//}
