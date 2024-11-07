package prueba.desarrolloFinal.servicios;

import java.util.List;
import java.util.Map;

import prueba.desarrolloFinal.modelos.Docente;

public interface IDocenteServicio {
    public List<Docente> obtenertodo();
    
    public Docente guardar(Docente docente);
    public Docente obtenerPorId(long legajo);
    public void eliminar(long legajo);
    
    Docente inscribirDocente(Map<String, Object> requestBody, Long cursoId);
}
