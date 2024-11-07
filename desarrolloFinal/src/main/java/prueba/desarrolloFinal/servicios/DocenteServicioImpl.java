package prueba.desarrolloFinal.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prueba.desarrolloFinal.modelos.Curso;
import prueba.desarrolloFinal.modelos.Docente;
import prueba.desarrolloFinal.repositorios.CursoRepositorio;
import prueba.desarrolloFinal.repositorios.DocenteRepositorio;


@Service
public class DocenteServicioImpl implements IDocenteServicio{
	@Autowired
    DocenteRepositorio docenteRepositorio;
	@Autowired
	private CursoRepositorio cursoRepositorio;

    @Override
    public List<Docente> obtenertodo() {
        return docenteRepositorio.findAll();
    }

    @Override
    public Docente guardar(Docente docente) {
        return docenteRepositorio.save(docente);
    }

    @Override
    public Docente obtenerPorId(long legajo) {
        return docenteRepositorio.findById(legajo).orElse(null);
    }

    @Override
    public void eliminar(long legajo) {
        docenteRepositorio.deleteById(legajo);
    }
    //--------logica de negocio ------------------
    public Docente obtenerDocentePorId(Long id) {
        return docenteRepositorio.findById(id)
            .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
    }
    
    //segundo end point para agregar a un nuevo docente a un curso
    @Override
    public Docente inscribirDocente(Map<String, Object> requestBody, Long cursoId) {
        // Extraer datos del docente del cuerpo de la solicitud
        Map<String, Object> docenteData = (Map<String, Object>) requestBody.get("docente");
        
        Docente docente = new Docente();
        docente.setNombre((String) docenteData.get("nombre"));
        // Agrega otros campos del docente según sea necesario

        // Verificar si el curso existe
        Curso curso = cursoRepositorio.findById(cursoId)
            .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Verificar si el curso ya tiene un docente asignado
        if (curso.getDocente() != null) {
            throw new IllegalStateException("El curso ya tiene un docente designado.");
        }

        // Asignar el curso al docente
        if (docente.getCursos() == null) {
            docente.setCursos(new ArrayList<>());
        }
        docente.getCursos().add(curso);

        // Guardar el nuevo docente en la base de datos
        docenteRepositorio.save(docente);

        // Asignar el docente al curso
        curso.setDocente(docente);
        cursoRepositorio.save(curso); // Actualizar el curso con el nuevo docente

        return docente;} // Devolver el objeto Docente creado  

}
