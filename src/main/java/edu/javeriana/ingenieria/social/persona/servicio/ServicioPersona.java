package edu.javeriana.ingenieria.social.persona.servicio;

import edu.javeriana.ingenieria.social.persona.dominio.Persona;
import edu.javeriana.ingenieria.social.persona.repositorio.RepositorioPersona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioPersona {
    @Autowired
    private RepositorioPersona repositorio;

    public List<Persona> obtenerPersonas(){
        return repositorio.findAll();
    }

    public List<Persona> obtenerPersonas(String tipoDocumento){
        return repositorio.findAllByTipoDocumento(tipoDocumento);
    }

    public List<Persona> obtenerPersonasPorEps(String eps){
        return repositorio.findAllByEps(eps);
    }

    public Persona obtenerPersona(Integer id){
        return repositorio.findById(id).orElse(null);
    }

    public Persona obtenerPersonaPorCedula(Integer cedula){
        return repositorio.findOneByCedula(cedula);
    }

    public Persona obtenerPersona(String correoElectronico){
        return repositorio.findAllByCorreoElectronico(correoElectronico);
    }

    public Persona crearPersona(Persona persona){
        return repositorio.save(persona);
    }

    public Persona actualizarPersona(Integer id, Persona persona){
        if(repositorio.findById(id).orElse(null) == null){
            return null;
        }
        persona.setId(id);
        return repositorio.save(persona);
    }

    public Persona borrarPersona(Integer id){
        Persona aux = repositorio.findById(id).orElse(null);
        if(aux == null) return null;
        repositorio.delete(aux);
        return aux;
    }

    public boolean personaExiste(Integer id){
        return repositorio.existsById(id);
    }

    public boolean personaExiste(String correoElectronico){
        return repositorio.existsByCorreoElectronico(correoElectronico);
    }

    public boolean personaExistePorCedula(Integer cedula){
        return repositorio.existsByCedula(cedula);
    }
}
