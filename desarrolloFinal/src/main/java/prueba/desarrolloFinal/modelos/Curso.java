package prueba.desarrolloFinal.modelos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;//agregado par el endpoint de relacion entre las tablas

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;//agregado para el endpoint de relacion entre las tablas
import jakarta.persistence.ManyToMany;//agregado para el end point de relacion
import jakarta.persistence.ManyToOne;

@Entity
public class Curso implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "tema_id", nullable = false)
    @JsonIgnoreProperties({"cursos"})
    private Tema tema;

    @ManyToOne
    @JoinColumn(name = "docente_legajo", nullable = false)
    @JsonIgnoreProperties({"cursos","alumnos"})
    private Docente docente;
    
    @Column(name = "fecha_inicio")
    private Date fecha_inicio;
    
    @Column(name = "fecha_fin")
    private Date fecha_fin;
 
    @Column(name = "precio")
    private double precio;

    @ManyToMany
    @JoinTable(
        name = "Alumno_curso",
        joinColumns = @JoinColumn(name = "curso_id"),
        inverseJoinColumns = @JoinColumn(name = "alumno_id")
    )
    @JsonIgnoreProperties({"cursos","docente"})
    private List<Alumno> alumnos;

    
    public Curso(Long id, Tema tema, double precio, Date fechaInicio, Date fechaFin, Docente docente) {
        this.id = id;
        this.tema = tema;
        this.precio = precio;
        this.fecha_inicio = fechaInicio;
        this.fecha_fin = fechaFin;
        this.docente = docente;
    }
    public Curso() {}
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public Date getFechaInicio() {
		return fecha_inicio;
	}

	public void setFechaInicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public Date getFechaFin() {
		return fecha_fin;
	}

	public void setFechaFin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	//agregado para hacer los endpoints de la union de las tablas 
    public List<Alumno> getAlumnos() {
        return alumnos;
    }
    
    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }	

    //-------------------incluye esto tambian-------------------------------------
}
