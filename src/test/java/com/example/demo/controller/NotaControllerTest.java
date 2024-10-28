//package com.example.demo.controller;
//
//import com.example.demo.servico.NotaService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(NotaController.class)
//public class NotaControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private NotaService notaService;
//
//    @Test
//    public void criarAlunoComSucesso() throws Exception {
//        mockMvc.perform(post("/aluno/criar")
//                        .param("nome", "João"))
//                .andExpect(status().isCreated())
//                .andExpect(content().string("Aluno João criado com sucesso!"));
//    }
//
//    @Test
//    public void adicionarNotaComSucesso() throws Exception {
//        mockMvc.perform(post("/aluno/nota")
//                        .param("nome", "João")
//                        .param("nota", "8.5"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Nota adicionada com sucesso para o aluno João"));
//    }
//
//    @Test
//    public void calcularMediaComSucesso() throws Exception {
//        when(notaService.calcularMedia("João")).thenReturn(7.0);
//
//        mockMvc.perform(get("/aluno/media")
//                        .param("nome", "João"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("A média do aluno João é 7.0"));
//    }
//
//    @Test
//    public void verificarAprovacao() throws Exception {
//        when(notaService.foiAprovado("João")).thenReturn(true);
//
//        mockMvc.perform(get("/aluno/status")
//                        .param("nome", "João"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Aluno João aprovado!"));
//    }
//}
