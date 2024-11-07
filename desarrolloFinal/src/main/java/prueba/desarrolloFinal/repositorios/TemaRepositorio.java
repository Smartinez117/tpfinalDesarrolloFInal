package prueba.desarrolloFinal.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import prueba.desarrolloFinal.modelos.Tema;

@Repository
public interface TemaRepositorio extends JpaRepository<Tema, Long> {

}
