package prueba.desarrolloFinal.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prueba.desarrolloFinal.modelos.Alumno;
import prueba.desarrolloFinal.modelos.Curso;
import prueba.desarrolloFinal.repositorios.AlumnoRepositorio;
import prueba.desarrolloFinal.repositorios.CursoRepositorio;

@Service
public class AlumnoServicioImpl implements IAlumnoServicio{
	   @Autowired
	   AlumnoRepositorio alumnoRepositorio;
	   
	   @Autowired
	    private CursoRepositorio cursoRepositorio;

	   @Override
	   public List<Alumno> obtenertodo() {
	       return alumnoRepositorio.findAll();
	   }

	   @Override //no sirve para inscribir a un alumno
	   public Alumno guardar(Alumno alumno) {
	       return alumnoRepositorio.save(alumno);
	   }

	   @Override
	   public Alumno obtenerPorId(long id) {
	       return alumnoRepositorio.findById(id).orElse(null);
	   }

	   @Override
	   public void eliminar(long id) {
	       alumnoRepositorio.deleteById(id);
	   }
	   //agregado para el endpoint de alumnos de un curso por id
	   @Override
	   public List<Alumno> obtenerAlumnosPorCurso(long cursoId) {
		   List<Alumno> alumnos = alumnoRepositorio.findAlumnosByCursoId(cursoId);
	       return alumnos;
	   }
	   //--------------------esto tambien------------------
	 //------------------LOGICA DE NEGOCIO----------------------------
	   //endpoint para inscribir a un nuevo alumno
	    @Override
	    public Alumno agregarAlumno(Alumno alumno, Long cursoId) {
	        // Obtener el curso por ID
	        Curso curso = cursoRepositorio.findById(cursoId)
	            .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
	        // Agregar el curso al alumno
	        if (alumno.getCursos() == null) {
	            alumno.setCursos(new ArrayList<>());
	        }
	        alumno.getCursos().add(curso);
	        curso.getAlumnos().add(alumno);

	        alumnoRepositorio.save(alumno);
	        cursoRepositorio.save(curso);
	        return alumno;
	    }
	   //FUNCIONES PARA que un alumno se inscriba a otro cursos
	    @Override
	    public Alumno cambiarCursos(Long alumnoId, List<Long> nuevoCursoIds) {
	        // Obtener el alumno por ID
	        Alumno alumno = alumnoRepositorio.findById(alumnoId)
	                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

	        // Obtener los cursos correspondientes a los IDs proporcionados
	        List<Curso> nuevosCursos = cursoRepositorio.findAllById(nuevoCursoIds);
	        
	        // Verificar si hay cursos no encontrados
	        if (nuevosCursos.size() != nuevoCursoIds.size()) {
	            throw new RuntimeException("Uno o más cursos no encontrados.");
	        }

	        // Obtener la lista actual de cursos del alumno
	        List<Curso> cursosActuales = alumno.getCursos();

	        // Inscribir al alumno en los nuevos cursos
	        for (Curso nuevoCurso : nuevosCursos) {
	            // Verificar si el alumno ya está inscrito en el curso
	            if (cursosActuales != null && cursosActuales.stream().anyMatch(curso -> curso.getId() == nuevoCurso.getId())) {
	                throw new RuntimeException("El alumno ya está inscrito en el curso con ID: " + nuevoCurso.getId());
	            }

	            // Agregar al alumno al nuevo curso
	            if (nuevoCurso.getAlumnos() == null) {
	                nuevoCurso.setAlumnos(new ArrayList<>());
	            }
	            nuevoCurso.getAlumnos().add(alumno);

	            // Agregar el curso a la lista de cursos del alumno
	            if (cursosActuales == null) {
	                cursosActuales = new ArrayList<>();
	                alumno.setCursos(cursosActuales);
	            }
	            cursosActuales.add(nuevoCurso);
	        }

	        // Actualizar las relaciones en la base de datos
	        alumnoRepositorio.save(alumno); // Guardar cambios en el alumno
	        cursoRepositorio.saveAll(nuevosCursos); // Guardar cambios en los nuevos cursos

	        return alumno; // Devolver el objeto Alumno actualizado  
	    }
	    //end point para que un alumno deje curso de acuerdo a los id
	    public Alumno dejarCursos(Long alumnoId, List<Long> cursoIdsADejar) {
	        // Obtener el alumno por ID
	        Alumno alumno = alumnoRepositorio.findById(alumnoId)
	                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

	        // Obtener la lista actual de cursos del alumno
	        List<Curso> cursosActuales = alumno.getCursos();

	        // Filtrar los cursos actuales para eliminar aquellos que el alumno quiere dejar
	        List<Curso> cursosAFiltrar = cursosActuales.stream()
	                .filter(curso -> !cursoIdsADejar.contains(curso.getId()))
	                .collect(Collectors.toList());

	        // Actualizar la lista de cursos del alumno
	        alumno.setCursos(cursosAFiltrar);

	        // Actualizar las relaciones en los cursos correspondientes
	        for (Long cursoId : cursoIdsADejar) {
	            Curso curso = cursoRepositorio.findById(cursoId)
	                    .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
	            
	            // Eliminar al alumno de la lista de alumnos del curso
	            if (curso.getAlumnos() != null) {
	                curso.getAlumnos().remove(alumno);
	            }
	            
	            // Guardar cambios en el curso
	            cursoRepositorio.save(curso);
	        }

	        // Guardar cambios en el alumno
	        return alumnoRepositorio.save(alumno); // Devolver el objeto Alumno actualizado
	    }
	 //-------------------LOGICA DE NEGOCIO----------------------------
	 
}
