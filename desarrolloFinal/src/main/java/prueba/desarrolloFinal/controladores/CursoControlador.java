package prueba.desarrolloFinal.controladores;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;

import prueba.desarrolloFinal.modelos.Curso;
import prueba.desarrolloFinal.servicios.CursoServicioImpl; 


@RestController 
@CrossOrigin(origins= "http://localhost:4200")
@RequestMapping("/api/v1/cursos") 
public class CursoControlador {
	@Autowired 
    private CursoServicioImpl cursoServicio; 

    //---------------------------GET------------------------------
    @GetMapping("/") 
    public List<Curso> obtenerCursos() { 
         return cursoServicio.obtenertodo(); 
    } 
    // Obtener un curso por ID
    @GetMapping("/{id}") 
    public ResponseEntity<Curso> obtenerCursoPorId(@PathVariable long id) { 
         Curso curso = cursoServicio.obtenerPorId(id); 
         if (curso == null) { 
              return ResponseEntity.notFound().build(); 
         } 
         return ResponseEntity.ok(curso); 
    } 
    //funcion para el primer endpoint para obtener curso que finaliza fecha especifica
    @GetMapping("/finalizan/{fecha_fin}")
    public ResponseEntity<List<Curso>> obtenerCursosPorFechaFin(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha_fin) {
        List<Curso> cursos = cursoServicio.obtenerCursosPorFechaFin(fecha_fin);
        return ResponseEntity.ok(cursos);
    }

   //---------------------------POST------------------------------
    @PostMapping("/guardar") 
    public ResponseEntity<Curso> guardarCurso(@RequestBody Curso curso) { 
         Curso nuevoCurso = cursoServicio.guardar(curso); 
         return new ResponseEntity<>(nuevoCurso, HttpStatus.CREATED); 
    }
    @PostMapping("/crearCurso")			// funciona
    public ResponseEntity<Curso> crearCurso(@RequestBody Map<String, Object> requestBody) {
        // Extraer el ID del tema y del docente del cuerpo de la solicitud
        Long temaId = ((Number) requestBody.get("temaId")).longValue();
        Long docenteLegajo = ((Number) requestBody.get("docenteLegajo")).longValue();

        // Llamar al servicio para crear el curso
        Curso nuevoCurso = cursoServicio.crearCurso(requestBody, temaId, docenteLegajo);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCurso);
    }
  //---------------------------PUT------------------------------
    // Actualizar un curso existente
    @PutMapping("/{id}") //solo actualiza los campos que esta relacionados con el curso no los de la tabla de relacion
    public ResponseEntity<Curso> actualizarCurso(@PathVariable long id, @RequestBody Curso curso) { 
         Curso cursoExistente = cursoServicio.obtenerPorId(id); 
         if (cursoExistente == null) { 
              return ResponseEntity.notFound().build(); 
         } 

         // Actualizar campos del curso existente
         //cursoExistente.setTema(curso.getTema());
         //cursoExistente.setDocente(curso.getDocente());
         cursoExistente.setFechaInicio(curso.getFechaInicio());
         cursoExistente.setFechaFin(curso.getFechaFin());
         cursoExistente.setPrecio(curso.getPrecio());

         Curso cursoActualizado = cursoServicio.guardar(cursoExistente); 
         return new ResponseEntity<>(cursoActualizado, HttpStatus.OK); 
    }
    //endpoitn para cambiar a un docente del curso
    @PutMapping("/{cursoId}/cambiarDocente")
    public ResponseEntity<Curso> cambiarDocente(
            @PathVariable Long cursoId,
            @RequestBody Map<String, Object> requestBody) {
        
        Long nuevoDocenteId = ((Number) requestBody.get("docenteId")).longValue();
        
        // Llamar al servicio para cambiar el docente del curso
        Curso cursoActualizado = cursoServicio.cambiarDocente(cursoId, nuevoDocenteId);
        
        return ResponseEntity.ok(cursoActualizado);
    }
    @PutMapping("/{cursoId}/cambiarTema")//falta chequear con el postman
    public ResponseEntity<Curso> cambiarTema(
            @PathVariable Long cursoId,
            @RequestBody Map<String, Object> requestBody) {
        
        Long nuevoTemaId = ((Number) requestBody.get("temaId")).longValue();
        
        // Llamar al servicio para cambiar el tema del curso
        Curso cursoActualizado = cursoServicio.cambiarTema(cursoId, nuevoTemaId);
        
        return ResponseEntity.ok(cursoActualizado);
    }
    //---------------------------DELETE------------------------------
    // Eliminar un curso por ID
    @DeleteMapping("/{id}") 
    public ResponseEntity<HashMap<String, Boolean>> eliminarCurso(@PathVariable long id) { 
         Curso cursoExistente = cursoServicio.obtenerPorId(id); 
         if (cursoExistente == null) { 
              return ResponseEntity.notFound().build(); 
         } 

         cursoServicio.eliminar(id); 
         HashMap<String, Boolean> estadoEliminado = new HashMap<>(); 
         estadoEliminado.put("eliminado", true); 
         return ResponseEntity.ok(estadoEliminado); 
    } 
    
}
