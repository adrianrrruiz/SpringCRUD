package edu.javeriana.tallernotasAOP.repositorio;

import edu.javeriana.tallernotasAOP.modelo.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioNota extends JpaRepository<Nota, Integer> {}
