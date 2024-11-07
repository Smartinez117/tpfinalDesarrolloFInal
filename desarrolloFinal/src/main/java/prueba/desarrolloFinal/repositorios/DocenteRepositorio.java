package prueba.desarrolloFinal.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import prueba.desarrolloFinal.modelos.Docente;

@Repository
public interface DocenteRepositorio extends JpaRepository<Docente, Long>{
	@Query("SELECT d FROM Docente d JOIN FETCH d.cursos c WHERE d.legajo = :docenteId")
	Optional<Docente> findDocenteConCursos(@Param("docenteId") Long docenteId);
}
