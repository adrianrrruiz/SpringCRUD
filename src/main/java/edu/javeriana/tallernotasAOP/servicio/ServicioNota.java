package edu.javeriana.tallernotasAOP.servicio;

import edu.javeriana.tallernotasAOP.excepcion.RegistroNoEncontradoException;
import edu.javeriana.tallernotasAOP.modelo.Nota;
import edu.javeriana.tallernotasAOP.repositorio.RepositorioNota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicioNota implements IServicioNota{

    @Autowired
    private RepositorioNota repositorioNota;

    @Override
    public List<Nota> traerTodas() {
        return repositorioNota.findAll();
    }

    @Override
    public Nota traeNotaPorId(Integer id) {
        return repositorioNota.findById(id)
                .orElseThrow(() -> new RegistroNoEncontradoException("No existe nota con id: " + id));
    }

    @Override
    @Transactional
    public Nota crearNota(Nota nota) {
        return repositorioNota.save(nota);
    }

    @Override
    @Transactional
    public Nota actualizarNota(Integer id, Nota nota) {
        Nota nuevaNota = repositorioNota.findById(id)
                .orElseThrow(() -> new RegistroNoEncontradoException("No existe la nota con id: " + id));

        nuevaNota.setObservacion(nota.getObservacion());
        nuevaNota.setValor(nota.getValor());
        nuevaNota.setPorcentaje(nota.getPorcentaje());

        return repositorioNota.save(nuevaNota);
    }

    @Override
    @Transactional
    public void borrarNota(Integer id) {
        Nota nota = repositorioNota.findById(id)
                .orElseThrow(() -> new RegistroNoEncontradoException("No existe la nota con id: " + id));
        repositorioNota.delete(nota);
    }

}
