package prueba.desarrolloFinal.controladores;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prueba.desarrolloFinal.modelos.Alumno;
import prueba.desarrolloFinal.servicios.AlumnoServicioImpl;

@RestController
@RequestMapping("/api/v1/alumnos")
@CrossOrigin(origins= "http://localhost:4200")
public class AlumnoControlador {
	   @Autowired
	   AlumnoServicioImpl alumnoServicio;
	   
	   //----------------uso de gets------------------
	   @GetMapping("/")
	   public List<Alumno> obtenerAlumnos() {
	       return alumnoServicio.obtenertodo();
	   }
	   
	   @GetMapping("/{id}")
	   public ResponseEntity<Alumno> obtenerAlumnoPorId(@PathVariable long id) {
	       Alumno alumno = alumnoServicio.obtenerPorId(id);
	       return ResponseEntity.ok(alumno);
	   }
	   //end point para saber los alumnos de un curso por id(arreglado!!!!)
	   @GetMapping("/{cursoId}/alumnos")
	   public ResponseEntity<List<Alumno>> obtenerAlumnosPorCurso(@PathVariable long cursoId) {
	       List<Alumno> alumnos = alumnoServicio.obtenerAlumnosPorCurso(cursoId);
	       return ResponseEntity.ok(alumnos);
	   }

	   //-----------------uso de post-------------------
	   @PostMapping("/guardar") //no sirve para usar con relacion de las tablas
	   public ResponseEntity<Alumno> guardarAlumno(@RequestBody Alumno alumno) {
	       Alumno nuevo_alumno = alumnoServicio.guardar(alumno);
	       return new ResponseEntity<>(nuevo_alumno, HttpStatus.CREATED);
	   }
	   //endpoint para ingresar un nuevo alumno
	   @PostMapping("/inscribirAlumno")
	   public ResponseEntity<Alumno> agregarAlumno(@RequestBody Map<String, Object> requestBody) {
	       // Extraer los datos del alumno
	       Map<String, Object> alumnoData = (Map<String, Object>) requestBody.get("alumno");
	       Alumno alumno = new Alumno();
	       alumno.setNombre((String) alumnoData.get("nombre"));
	       // Convertir la fecha desde String a Date
	       String fechaNacimientoStr = (String) alumnoData.get("fecha_nacimiento");
	       Date fechaNacimiento = java.sql.Date.valueOf(fechaNacimientoStr); // Asumiendo que el formato es "yyyy-MM-dd"
	       alumno.setFechaNacimiento(fechaNacimiento);
	       // Extraer el cursoId	
	       Long cursoId = ((Number) requestBody.get("cursoId")).longValue();
	       Alumno nuevoAlumno = alumnoServicio.agregarAlumno(alumno, cursoId);
	       return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAlumno);
	    }

	   //------------------uso de los put-----------------------
	    @PutMapping("/{id}")
	    public ResponseEntity<Alumno> actualizar(@PathVariable long id, @RequestBody Alumno alumno) {
	        Alumno alumnoPorId = alumnoServicio.obtenerPorId(id);
	        if (alumnoPorId == null) {
	            return ResponseEntity.notFound().build();
	        }
	        
	        alumnoPorId.setNombre(alumno.getNombre());
	        alumnoPorId.setFechaNacimiento(alumno.getFechaNacimiento());
	        
	        Alumno alumno_actualizado = alumnoServicio.guardar(alumnoPorId);
	        return new ResponseEntity<>(alumno_actualizado, HttpStatus.OK);
	    }
	    
	    @PutMapping("/{alumnoId}/inscribirseCurso")//permite a un alumno cambiar de los cursos a los que esta inscripto(list)
	    public ResponseEntity<Alumno> cambiarCursosAlumno(
	            @PathVariable Long alumnoId,
	            @RequestBody Map<String, Object> requestBody) {
	       
	        // Obtener la lista de cursoIds del cuerpo de la solicitud
	        List<Long> cursoIds = (List<Long>) requestBody.get("cursoIds");
	        
	        // Llamar al servicio para inscribir al alumno en los nuevos cursos
	        Alumno alumnoActualizado = alumnoServicio.cambiarCursos(alumnoId, cursoIds);
	        
	        return ResponseEntity.ok(alumnoActualizado);
	    }
	    @PutMapping("/{alumnoId}/dejarCursos")//permite aq un alumno dejar 
	    public ResponseEntity<Alumno> dejarCursosAlumno(
	            @PathVariable Long alumnoId,
	            @RequestBody Map<String, Object> requestBody) {
	        
	        // Obtener la lista de cursoIds que el alumno quiere dejar
	        List<Long> cursoIdsADejar = (List<Long>) requestBody.get("cursoIds");
	        
	        // Llamar al servicio para que el alumno deje los cursos especificados
	        Alumno alumnoActualizado = alumnoServicio.dejarCursos(alumnoId, cursoIdsADejar);
	        
	        return ResponseEntity.ok(alumnoActualizado);
	    }
	    //-----------------uso del delete------------------------------
	   @DeleteMapping("/{id}")
	   public ResponseEntity<HashMap<String, Boolean>> eliminarAlumno(@PathVariable long id) {
	       alumnoServicio.eliminar(id);
	       HashMap<String, Boolean> estadoEliminado = new HashMap<>();
	       estadoEliminado.put("eliminado", true);
	       return ResponseEntity.ok(estadoEliminado);
	   }
}