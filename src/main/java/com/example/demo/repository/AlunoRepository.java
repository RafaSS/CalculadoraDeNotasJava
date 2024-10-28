// src/main/java/com/example/demo/repository/AlunoRepository.java
package com.example.demo.repository;

import com.example.demo.modelo.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Aluno buscar(String nome);
    Aluno findById(long id);
}
