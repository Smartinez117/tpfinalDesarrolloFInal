package prueba.desarrolloFinal.servicios;

import java.util.List;

import prueba.desarrolloFinal.modelos.Alumno;
//hace falta declrar todas las funciones para
public interface IAlumnoServicio {
	public List<Alumno> obtenertodo();
	public Alumno guardar(Alumno alumno);
	public Alumno obtenerPorId(long id);
	public void eliminar(long id);
    // Nuevo método para obtener alumnos por curso
    List<Alumno> obtenerAlumnosPorCurso(long cursoId);
    
    // Nuevo método para obtener un alumno con sus cursos
 
    Alumno agregarAlumno(Alumno alumno, Long cursoId);
    
    Alumno cambiarCursos(Long alumnoId, List<Long> nuevoCursoIds);
    
    
}
