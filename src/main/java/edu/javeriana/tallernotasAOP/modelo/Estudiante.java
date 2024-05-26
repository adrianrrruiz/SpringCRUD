package edu.javeriana.tallernotasAOP.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Table
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String apellido;
    private String correo;
    @OneToMany(mappedBy = "estudiante_id")
    //@OneToMany(fetch=FetchType.LAZY, cascade = {CascadeType.ALL})
    //@JoinColumn(name="estudiante_id")
    private Set<Nota> notas;
}
