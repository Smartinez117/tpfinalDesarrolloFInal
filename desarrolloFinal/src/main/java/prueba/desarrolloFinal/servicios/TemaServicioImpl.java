package prueba.desarrolloFinal.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prueba.desarrolloFinal.modelos.Tema;
import prueba.desarrolloFinal.repositorios.TemaRepositorio;

@Service
public class TemaServicioImpl implements ITemaServicio{
	   @Autowired
	   TemaRepositorio temaRepositorio;
	   
	   @Override
	   public List<Tema> obtenertodo() {
	       return temaRepositorio.findAll();
	   }

	   @Override
	   public Tema guardar(Tema tema) {
	       return temaRepositorio.save(tema);
	   }

	   @Override
	   public Tema obtenerPorId(long id) {
	       return temaRepositorio.findById(id).orElse(null);
	   }

	   @Override
	   public void eliminar(long id) {
	       temaRepositorio.deleteById(id);
	   }
	    @Override
	    public Tema obtenerTemaPorId(Long id) {
	        return temaRepositorio.findById(id)
	            .orElse(null); // Devuelve null si no se encuentra el tema
	    }

}
