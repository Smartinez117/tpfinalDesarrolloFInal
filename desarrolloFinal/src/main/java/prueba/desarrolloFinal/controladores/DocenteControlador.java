package prueba.desarrolloFinal.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import prueba.desarrolloFinal.modelos.Docente;
import prueba.desarrolloFinal.servicios.DocenteServicioImpl;


@RestController
@CrossOrigin(origins= "http://localhost:4200")
@RequestMapping("/api/v1/docentes")
public class DocenteControlador{
	 @Autowired
	 DocenteServicioImpl docenteServicio;

	 //------------------GET----------------------
	 @GetMapping("/")
	 public List<Docente> obtenerDocentes() {
	        return docenteServicio.obtenertodo();
	 }
	 @GetMapping("/{legajo}")
	 public ResponseEntity<Docente> obtenerDocentePorLegajo(@PathVariable long legajo) {
	     Docente docente = docenteServicio.obtenerPorId(legajo);
	     return ResponseEntity.ok(docente);
	 }
	 //--------------------POST--------------------
	 @PostMapping("/guardar")
	 public ResponseEntity<Docente> guardarDocente(@RequestBody Docente docente) {
	    Docente nuevo_docente = docenteServicio.guardar(docente);
	    return new ResponseEntity<>(nuevo_docente, HttpStatus.CREATED);
	 }
	 //post para inscribir a un docente y asignarle un curso.
	 @PostMapping("/inscribirDocente")// no usar pues la logica sera crear un docente y el curso es donde asigna al docente
	 public ResponseEntity<Docente> inscribirDocente(@RequestBody Map<String, Object> requestBody) {
	    // Extraer el cursoId del cuerpo de la solicitud
	     Long cursoId = ((Number) requestBody.get("cursoId")).longValue();

	     // Llamar al servicio para inscribir al docente
	     Docente nuevoDocente = docenteServicio.inscribirDocente(requestBody, cursoId);
	        
	     return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDocente);
	    }
	 //---------------------PUT------------------------------
	 @PutMapping("/{legajo}")
	 public ResponseEntity<Docente> actualizar(@PathVariable long legajo, @RequestBody Docente docente) {
	   Docente docentePorLegajo = docenteServicio.obtenerPorId(legajo);
	   if (docentePorLegajo == null) {
	        return ResponseEntity.notFound().build();
	    }
	        
	        docentePorLegajo.setNombre(docente.getNombre());
	        
	        Docente docente_actualizado = docenteServicio.guardar(docentePorLegajo);
	        return new ResponseEntity<>(docente_actualizado, HttpStatus.OK);
	    }	 
	    //-----------------------DELETE-----------------------
	 @DeleteMapping("/{legajo}")
	 public ResponseEntity<HashMap<String, Boolean>> eliminarDocente(@PathVariable long legajo) {
	     this.docenteServicio.eliminar(legajo);
	     
	     HashMap<String, Boolean> estadoEliminado = new HashMap<>();
	     estadoEliminado.put("eliminado", true);
	     return ResponseEntity.ok(estadoEliminado);
	 }
}

