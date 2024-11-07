package prueba.desarrolloFinal.repositorios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import prueba.desarrolloFinal.modelos.Curso;

public interface CursoRepositorio extends JpaRepository<Curso, Long>{
	//funcion para el primer endpoint para obtener curso que finaliza fecha especifica
	@Query("SELECT c FROM Curso c WHERE c.fecha_fin = :fecha_fin")
	List<Curso> findByFechaFin(Date fecha_fin);
	
	//funcion para el primer endpoint para obtener curso que finaliza fecha especifica
    @Query("SELECT c FROM Curso c JOIN FETCH c.alumnos a WHERE c.id = :cursoId")
    Optional<Curso> findCursoConAlumnos(@Param("cursoId") Long cursoId);
    
   //funcion para obtener a un curso con id , y los alumnos de dicho curso.
}
