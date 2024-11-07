package prueba.desarrolloFinal.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prueba.desarrolloFinal.modelos.Curso;
import prueba.desarrolloFinal.modelos.Docente;
import prueba.desarrolloFinal.modelos.Tema;
import prueba.desarrolloFinal.repositorios.AlumnoRepositorio;
import prueba.desarrolloFinal.repositorios.CursoRepositorio;
import prueba.desarrolloFinal.repositorios.DocenteRepositorio;
import prueba.desarrolloFinal.repositorios.TemaRepositorio;

@Service
public class CursoServicioImpl implements ICursoServicio{
	   @Autowired
	   private CursoRepositorio cursoRepositorio;
	   
	   @Autowired
	   private TemaRepositorio temaRepositorio;
	   
	   @Autowired
	   private DocenteRepositorio docenteRepositorio;
	   
	   @Autowired
	    private AlumnoRepositorio alumnoRepositorio;

	   @Override
	   public List<Curso> obtenertodo() {
	       return cursoRepositorio.findAll();
	   }

	   @Override
	   public Curso guardar(Curso curso) {
	       return cursoRepositorio.save(curso);
	   }

	   @Override
	   public Curso obtenerPorId(long id) {
	       return cursoRepositorio.findById(id).orElse(null);
	   }

	   @Override
	   public void eliminar(long id) {
	       cursoRepositorio.deleteById(id);
	   }
	 //funcion para el primer endpoint para obtener curso que finaliza fecha especifica(arreglado)
	   public List<Curso> obtenerCursosPorFechaFin(Date fecha_fin) {
		   List<Curso> cursos = cursoRepositorio.findByFechaFin(fecha_fin);
		    return cursos;
		}	   
	 //funcion para el primer endpoint para obtener curso que finaliza fecha especifica

	   //-------------endpopint logica de negocio------------------ 
	   //endpoint para crear un nuevo curso, asignar un tema y un docente
	   @Override
	   public Curso crearCurso(Map<String, Object> requestBody, Long temaId, Long docenteLegajo) {
	        // Extraer datos del curso del cuerpo de la solicitud
	        Map<String, Object> cursoData = (Map<String, Object>) requestBody.get("curso");
	        
	        if (cursoData == null || !cursoData.containsKey("fecha_inicio") || !cursoData.containsKey("fecha_fin") || !cursoData.containsKey("precio")) {
	            throw new RuntimeException("Datos del curso incompletos o inválidos.");
	        }

	        String fechaInicioStr = (String) cursoData.get("fecha_inicio");
	        String fechaFinStr = (String) cursoData.get("fecha_fin");
	        
	        // Convertir las fechas usando java.sql.Date.valueOf
	        Date fechaInicio = java.sql.Date.valueOf(fechaInicioStr); // Asumiendo que el formato es "yyyy-MM-dd"
	        Date fechaFin = java.sql.Date.valueOf(fechaFinStr);

	        double precio = ((Number) cursoData.get("precio")).doubleValue();

	        // Verificar si el tema existe
	        Tema tema = temaRepositorio.findById(temaId)
	            .orElseThrow(() -> new RuntimeException("Tema no encontrado"));

	        // Verificar si el docente existe
	        Docente docente = docenteRepositorio.findById(docenteLegajo)
	            .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

	        // Crear el nuevo curso
	        Curso curso = new Curso();
	        curso.setTema(tema); // Asignar el tema al curso
	        curso.setDocente(docente); // Asignar el docente al curso
	        curso.setFechaInicio(fechaInicio);
	        curso.setFechaFin(fechaFin);
	        curso.setPrecio(precio);

	        // Agregar el curso a la lista de cursos del tema
	        if (tema.getCursos() == null) {
	            tema.setCursos(new ArrayList<>());
	        }
	        tema.getCursos().add(curso);

	        // Agregar el curso a la lista de cursos del docente
	        if (docente.getCursos() == null) {
	            docente.setCursos(new ArrayList<>());
	        }
	        docente.getCursos().add(curso);

	        // Guardar el nuevo curso en la base de datos
	        cursoRepositorio.save(curso);
	        
	        // También guarda los cambios en el tema y en el docente
	        temaRepositorio.save(tema);
	        docenteRepositorio.save(docente);

	        return curso;
	    }
	    
	   //endpoint para cambiar el docente de un curso
	   @Override
	    public Curso cambiarDocente(Long cursoId, Long nuevoDocenteId) {
	        // Obtener el curso por ID
	        Curso curso = cursoRepositorio.findById(cursoId)
	                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

	        // Obtener el nuevo docente por ID
	        Docente nuevoDocente = docenteRepositorio.findById(nuevoDocenteId)
	                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

	        // Cambiar la relación del docente en el curso
	        curso.setDocente(nuevoDocente);

	        // Guardar cambios en la base de datos
	        cursoRepositorio.save(curso); // Guardar y devolver el curso actualizado
	        return curso;
	   }
	   //endpoint para cambiar el tema de un curso 
	    @Override
	    public Curso cambiarTema(Long cursoId, Long nuevoTemaId) {
	        // Obtener el curso por ID
	        Curso curso = cursoRepositorio.findById(cursoId)
	                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

	        // Obtener el nuevo tema por ID
	        Tema nuevoTema = temaRepositorio.findById(nuevoTemaId)
	                .orElseThrow(() -> new RuntimeException("Tema no encontrado"));

	        // Cambiar la relación del tema en el curso
	        curso.setTema(nuevoTema);

	        // Guardar cambios en la base de datos
	        cursoRepositorio.save(curso);
	        return curso;
	    }
}
