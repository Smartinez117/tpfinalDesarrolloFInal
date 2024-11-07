package prueba.desarrolloFinal.repositorios;

import java.util.List;	//agregado para el endpopint de alumnos por id de curso

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; //agregado para el end point de alumnos por id de curso
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import prueba.desarrolloFinal.modelos.Alumno;

@Repository
public interface AlumnoRepositorio extends JpaRepository<Alumno, Long>{
	//agregado para funcione el iltimo endpoint
	@Query("SELECT a FROM Alumno a JOIN a.cursos c WHERE c.id = :cursoId")
	List<Alumno> findAlumnosByCursoId(long cursoId);
	
	@Query("SELECT a FROM Alumno a JOIN FETCH a.cursos c WHERE a.id = :alumnoId")
    Alumno findAlumnosConCursos(@Param("alumnoId") Long alumnoId);
	
	
}
