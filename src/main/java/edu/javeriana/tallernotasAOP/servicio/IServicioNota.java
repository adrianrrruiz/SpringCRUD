package edu.javeriana.tallernotasAOP.servicio;

import edu.javeriana.tallernotasAOP.modelo.Nota;

import java.util.List;

public interface IServicioNota {
    List<Nota> traerTodas();
    Nota traeNotaPorId(Integer id);
    Nota crearNota(Nota nota);
    Nota actualizarNota(Integer id, Nota nota);
    void borrarNota(Integer id);
}
