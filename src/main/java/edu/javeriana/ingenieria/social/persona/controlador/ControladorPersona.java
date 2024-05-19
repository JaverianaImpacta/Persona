package edu.javeriana.ingenieria.social.persona.controlador;

import edu.javeriana.ingenieria.social.persona.dominio.Persona;
import edu.javeriana.ingenieria.social.persona.servicio.ServicioPersona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
@CrossOrigin(origins="http://localhost:4200")
public class ControladorPersona {

    @Autowired
    private ServicioPersona servicio;

    @GetMapping("listar")
    public List<Persona> obtenerPersonas(){
        return servicio.obtenerPersonas();
    }

    @GetMapping("obtenerEps")
    public ResponseEntity<List<Persona>> obtenerPersonasPorEps(@RequestParam String eps){
        if(eps == null || eps.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(servicio.obtenerPersonasPorEps(eps).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(servicio.obtenerPersonasPorEps(eps), HttpStatus.OK);
    }

    @GetMapping("obtenerTipoDocumento")
    public ResponseEntity<List<Persona>> obtenerPersonas(@RequestParam String tipoDocumento){
        if(tipoDocumento == null || tipoDocumento.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(servicio.obtenerPersonas(tipoDocumento).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(servicio.obtenerPersonas(tipoDocumento), HttpStatus.OK);
    }

    @GetMapping("obtener")
    public ResponseEntity<Persona> obtenerPersona(@RequestParam Integer id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(servicio.obtenerPersona(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(servicio.obtenerPersona(id), HttpStatus.OK);
    }

    @GetMapping("obtenerCedula")
    public ResponseEntity<Persona> obtenerPersonaPorCedula(@RequestParam("cedula") Integer cedula){
        if(cedula == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!servicio.personaExistePorCedula(cedula)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(servicio.obtenerPersonaPorCedula(cedula), HttpStatus.OK);
    }

    @GetMapping("obtenerCorreoElectronico")
    public ResponseEntity<Persona> obtenerPersona(@RequestParam String correoElectronico){
        if(correoElectronico == null || correoElectronico.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!servicio.personaExiste(correoElectronico)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(servicio.obtenerPersona(correoElectronico), HttpStatus.OK);
    }

    @PostMapping("crear")
    public ResponseEntity<Persona> crearPersona(@RequestBody Persona persona){
        if(persona == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(servicio.personaExiste(persona.getId()) || servicio.personaExistePorCedula(persona.getCedula()) || servicio.personaExiste(persona.getCorreoElectronico())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(servicio.crearPersona(persona), HttpStatus.CREATED);
    }

    @PutMapping("actualizar")
    public ResponseEntity<Persona> actualizarPersona(@RequestParam Integer id, @RequestBody Persona persona){
        if(id == null || persona == null || !id.equals(persona.getId())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(servicio.actualizarPersona(id, persona) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(persona, HttpStatus.OK);
    }

    @DeleteMapping("eliminar")
    public ResponseEntity<HttpStatus> eliminarPersona(@RequestParam Integer id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(servicio.borrarPersona(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
