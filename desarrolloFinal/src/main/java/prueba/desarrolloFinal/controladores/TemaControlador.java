package prueba.desarrolloFinal.controladores;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import prueba.desarrolloFinal.modelos.Tema;
import prueba.desarrolloFinal.servicios.TemaServicioImpl;


@RestController
@CrossOrigin(origins= "http://localhost:4200")
@RequestMapping("/api/v1/temas")
public class TemaControlador {
    @Autowired
    private TemaServicioImpl temaServicio;
    //---------------------GET-------------------------------
    @GetMapping("/")
    public List<Tema> obtenerTemas() {
        return temaServicio.obtenertodo();}
    // Obtener un tema por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tema> obtenerTemaPorId(@PathVariable long id) {
        Tema tema = temaServicio.obtenerPorId(id);
        if (tema == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tema);
    }
    //---------------------POST-------------------------------
    // Guardar un nuevo tema
    @PostMapping("/guardar")
    public ResponseEntity<Tema> guardarTema(@RequestBody Tema tema) {
        Tema nuevoTema = temaServicio.guardar(tema);
        return new ResponseEntity<>(nuevoTema, HttpStatus.CREATED);}
    //---------------------PUT-------------------------------
    // Actualizar un tema existente
    @PutMapping("/{id}")
    public ResponseEntity<Tema> actualizarTema(@PathVariable long id, @RequestBody Tema tema) {
        Tema temaExistente = temaServicio.obtenerPorId(id);
        if (temaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        // Actualizar los campos del tema existente
        temaExistente.setNombre(tema.getNombre());
        temaExistente.setDescripcion(tema.getDescripcion());

        Tema temaActualizado = temaServicio.guardar(temaExistente);
        return new ResponseEntity<>(temaActualizado, HttpStatus.OK);
    }
    //---------------------DELETE-------------------------------
    // Eliminar un tema por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Boolean>> eliminarTema(@PathVariable long id) {
        Tema temaExistente = temaServicio.obtenerPorId(id);
        if (temaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        temaServicio.eliminar(id);
        HashMap<String, Boolean> estadoEliminado = new HashMap<>();
        estadoEliminado.put("eliminado", true);
        return ResponseEntity.ok(estadoEliminado);
    }
}
