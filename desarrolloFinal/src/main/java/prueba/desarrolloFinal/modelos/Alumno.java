package prueba.desarrolloFinal.modelos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Alumno implements Serializable{
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; 
   
    private String nombre;
    
    @Column(name = "fecha_nacimiento")
    private Date fecha_nacimiento;
	//agregado para que funcion el ultimo endpoint
	@ManyToMany(mappedBy = "alumnos")
	@JsonIgnoreProperties({"alumnos","docente","tema"})
	private List<Curso> cursos;
      
    public Alumno(Long id, String nombre, Date nacimiento, List<Curso> cursos) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_nacimiento = nacimiento;
        this.cursos = cursos;
    }
    public Alumno() {}
    
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaNacimiento() {
		return fecha_nacimiento;
	}

	public void setFechaNacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	//agregado para que funcion el ultimo endpoint
	//----------------logica de negocio-------------------
    public List<Curso> getCursos() { // Método getter para cursos
        return cursos;
    }
	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}
    
	//-------------------logica de negocio------------
	
    
}
