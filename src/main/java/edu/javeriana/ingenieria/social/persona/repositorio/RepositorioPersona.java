package edu.javeriana.ingenieria.social.persona.repositorio;

import edu.javeriana.ingenieria.social.persona.dominio.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioPersona extends JpaRepository<Persona, Integer> {

    Persona findOneByCedula(Integer cedula);

    List<Persona> findAllByTipoDocumento(String tipoDocumento);

    List<Persona> findAllByEps(String eps);

    boolean existsByCedula(Integer cedula);

    boolean existsByCorreoElectronico(String correoElectronico);

    Persona findAllByCorreoElectronico(String correoElectronico);
}
