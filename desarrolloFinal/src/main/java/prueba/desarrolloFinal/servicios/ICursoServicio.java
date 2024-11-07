package prueba.desarrolloFinal.servicios;

import java.util.List;
import java.util.Map;

import prueba.desarrolloFinal.modelos.Curso;

public interface ICursoServicio {
	public List<Curso> obtenertodo();
	public Curso guardar(Curso curso);
	public Curso obtenerPorId(long id);
	public void eliminar(long id);
	

	public Curso crearCurso(Map<String, Object> requestBody, Long temaId, Long docenteLegajo);
	public Curso cambiarDocente(Long cursoId, Long nuevoDocenteId);
	public Curso cambiarTema(Long cursoId, Long nuevoTemaId);
}
