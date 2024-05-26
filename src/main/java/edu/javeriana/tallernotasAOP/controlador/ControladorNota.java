package edu.javeriana.tallernotasAOP.controlador;

import edu.javeriana.tallernotasAOP.excepcion.RegistroNoEncontradoException;
import edu.javeriana.tallernotasAOP.modelo.Estudiante;
import edu.javeriana.tallernotasAOP.modelo.Nota;
import edu.javeriana.tallernotasAOP.repositorio.RepositorioNota;
import edu.javeriana.tallernotasAOP.servicio.ServicioNota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ControladorNota {

    @Autowired
    private ServicioNota servicioNota;

    @Autowired
    private RepositorioNota repositorioNota;

    @GetMapping("/notas")
    public List<Nota> traerTodas() {
        return servicioNota.traerTodas();
    }

    @GetMapping("/nota/{id}")
    public ResponseEntity<Nota> traeNota(@PathVariable Integer id)
    {
        Nota nota = servicioNota.traeNotaPorId(id);

        return ResponseEntity.ok(nota);
    }

    @PostMapping("/nota/crea")
    public Nota creaNota(@RequestBody  Nota nota) {
        return servicioNota.crearNota(nota);
    }

    //PUT  Update
    @PutMapping("/nota/act/{id}")
    public Nota actualizaNota(@PathVariable Integer id, @RequestBody Nota nota) {
        return  servicioNota.actualizarNota(id, nota);   //ResponseEntity.ok(nuevoEstudiante);
    }

    //DELETE Delete
    @DeleteMapping("/nota/borra/{id}")

    public ResponseEntity<HttpStatus> borraNota(@PathVariable Integer id) {
        servicioNota.borrarNota(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
