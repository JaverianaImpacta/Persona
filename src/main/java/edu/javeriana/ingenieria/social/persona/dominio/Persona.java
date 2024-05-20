package edu.javeriana.ingenieria.social.persona.dominio;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int cedula;

    @Column(name="tipo_documento")
    private String tipoDocumento;

    @Column(name="primer_nombre")
    private String primerNombre;

    @Column(name="segundo_nombre")
    private String segundoNombre;

    @Column(name="primer_apellido")
    private String primerApellido;

    @Column(name="segundo_apellido")
    private String segundoApellido;

    @Column(name="correo_electronico")
    private String correoElectronico;

    @Column(name="fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name="numero_celular")
    private Integer numeroCelular;

    private String sexo, eps, direccion;

}
