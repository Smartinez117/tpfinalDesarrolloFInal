package prueba.desarrolloFinal.modelos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class Docente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private long legajo; 
    private String nombre;
    
    @OneToMany(mappedBy = "docente")
    @JsonIgnoreProperties({"alumnos","docente"})
    private List<Curso> cursos; 

    public Docente(long legajo,String nombre) {
        this.nombre = nombre;
        this.legajo = legajo;
    }
    public Docente() {}
    
	public long getLegajo() {
		return legajo;
	}

	public void setLegajo(long legajo) {
		this.legajo = legajo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<Curso> getCursos() {
		return cursos;
	}
	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

    
	
}
