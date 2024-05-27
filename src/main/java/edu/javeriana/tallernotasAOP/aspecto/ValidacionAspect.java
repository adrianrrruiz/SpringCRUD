package edu.javeriana.tallernotasAOP.aspecto;


import edu.javeriana.tallernotasAOP.excepcion.ValidacionException;
import edu.javeriana.tallernotasAOP.modelo.Nota;
import edu.javeriana.tallernotasAOP.servicio.ServicioNota;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class ValidacionAspect {

    @Autowired
    private ServicioNota servicioNota;

    @Before("execution(* edu.javeriana.tallernotasAOP.servicio.ServicioNota.crearNota(..)) && args(nota)")
    public void validarPorcentajeCrear(Nota nota){
        validarPorcentaje(nota);
    }

    @Before("execution(* edu.javeriana.tallernotasAOP.servicio.ServicioNota.actualizarNota(..)) && args(id, nota)")
    public void validarPorcentajeActualizar(Integer id, Nota nota){
        validarPorcentajeAct(id, nota);
    }

    private void validarPorcentaje(Nota nota){
        List<Nota> notas = servicioNota.traerTodas();
        double totalPorcentaje = 0;
        for(Nota n : notas){
            if(n.getEstudiante_id().equals(nota.getEstudiante_id())){
                totalPorcentaje += n.getPorcentaje();
            }
        }

        totalPorcentaje += nota.getPorcentaje();

        if (totalPorcentaje > 100) {
            throw new ValidacionException("El porcentaje total de las notas del estudiante supera el 100%");
        }
    }

    private void validarPorcentajeAct(Integer id, Nota nota){
        List<Nota> notas = servicioNota.traerTodas();
        int id_estudiante = servicioNota.traeNotaPorId(id).getEstudiante_id();
        double totalPorcentaje = 0;

        for(Nota n : notas){
            if(n.getEstudiante_id().equals(id_estudiante)){
                totalPorcentaje += n.getPorcentaje();
            }
        }

        totalPorcentaje -= servicioNota.traeNotaPorId(id).getPorcentaje();

        totalPorcentaje += nota.getPorcentaje();

        System.out.println("total porcentaje: " + totalPorcentaje);

        if (totalPorcentaje > 100) {
            throw new ValidacionException("El porcentaje total de las notas del estudiante supera el 100%");
        }
    }
}
