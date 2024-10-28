// src/main/java/com/example/demo/repository/MateriaRepository.java
package com.example.demo.repository;

import com.example.demo.modelo.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
    Materia findById(long id);
    Materia save(Materia materia);
}
