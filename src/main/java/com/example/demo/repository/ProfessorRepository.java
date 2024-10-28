// src/main/java/com/example/demo/repository/ProfessorRepository.java
package com.example.demo.repository;

import com.example.demo.modelo.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Professor findById(long id);
    Professor criar(Professor professor);
}
