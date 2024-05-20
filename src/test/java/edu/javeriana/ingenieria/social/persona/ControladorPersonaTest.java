package edu.javeriana.ingenieria.social.persona;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.javeriana.ingenieria.social.persona.dominio.Persona;
import edu.javeriana.ingenieria.social.persona.servicio.ServicioPersona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ControladorPersonaTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@BeforeEach
	public void limpiarYRecargarBaseDatos() throws SQLException {
		// Leer el script SQL desde el archivo
		ClassPathResource resource = new ClassPathResource("Script.sql");
		ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), resource);
		resource = new ClassPathResource("Inserts-Prueba.sql");
		ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), resource);
	}

	@Test
	public void testObtenerPersonas() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/personas/listar"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(12));
	}
	@Test
	public void testObtenerPersonasPorEps() throws Exception {
		String eps = "Sura";
		mockMvc.perform(MockMvcRequestBuilders.get("/api/personas/obtenerEps?eps=" + eps))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(4));
	}

	@Test
	public void testObtenerPersonasPorTipoDocumento() throws Exception {
		String tipoDocumento = "CC"; // Suponiendo que se desea obtener personas con este tipo de documento
		mockMvc.perform(MockMvcRequestBuilders.get("/api/personas/obtenerTipoDocumento?tipoDocumento=" + tipoDocumento))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(12));
	}
	@Test
	public void testObtenerPersonaPorId() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/personas/obtener?id=1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.cedula").value(1111111))
				.andExpect(MockMvcResultMatchers.jsonPath("$.primerNombre").value("Pedro"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.correoElectronico").value("pedro.ramirez@example.com"));
	}

	@Test
	public void testObtenerPersonaPorCedula() throws Exception {
		Persona persona = servicioPersona.obtenerPersonaPorCedula(1234567);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/personas/obtenerCedula?cedula=1234567"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(10))
				.andExpect(MockMvcResultMatchers.jsonPath("$.cedula").value(1234567))
				.andExpect(MockMvcResultMatchers.jsonPath("$.primerNombre").value("Juan"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.correoElectronico").value("juan.perez@example.com"));
	}
	@Test
	public void testObtenerPersonaPorCorreoElectronico() throws Exception {
		String correoElectronico = "juan.perez@example.com";
		mockMvc.perform(MockMvcRequestBuilders.get("/api/personas/obtenerCorreoElectronico?correoElectronico=" + correoElectronico))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(10))
				.andExpect(MockMvcResultMatchers.jsonPath("$.cedula").value(1234567))
				.andExpect(MockMvcResultMatchers.jsonPath("$.primerNombre").value("Juan"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.correoElectronico").value(correoElectronico));
	}

	@Test
	public void testCrearPersona() throws Exception {
		Persona nuevaPersona = new Persona();
		nuevaPersona.setCedula(999559);
		nuevaPersona.setTipoDocumento("CC");
		nuevaPersona.setPrimerNombre("Nuevo");
		nuevaPersona.setPrimerApellido("Usuario");
		nuevaPersona.setCorreoElectronico("nuevo.usuario@example.com");
		nuevaPersona.setFechaNacimiento(Date.valueOf("1990-01-01"));
		nuevaPersona.setNumeroCelular(12345678);
		nuevaPersona.setSexo("M");
		nuevaPersona.setEps("Sura");
		nuevaPersona.setDireccion("Calle Falsa 123");
		nuevaPersona.setId(21);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/personas/crear")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(nuevaPersona)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void testActualizarPersona() throws Exception {
		Persona personaActualizada = new Persona();
		personaActualizada.setCedula(1111111);
		personaActualizada.setTipoDocumento("CC");
		personaActualizada.setPrimerNombre("Nuevo");
		personaActualizada.setPrimerApellido("Usuario");
		personaActualizada.setCorreoElectronico("nuevo.usuario@example.com");
		personaActualizada.setFechaNacimiento(Date.valueOf("1990-01-01"));
		personaActualizada.setNumeroCelular(12345678);
		personaActualizada.setSexo("M");
		personaActualizada.setEps("Sura");
		personaActualizada.setDireccion("Calle Falsa 123");
		personaActualizada.setId(1);
		mockMvc.perform(MockMvcRequestBuilders.put("/api/personas/actualizar?id=" + 1)
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(personaActualizada)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.primerNombre").value("Nuevo"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.cedula").value(1111111))
				.andExpect(MockMvcResultMatchers.jsonPath("$.correoElectronico").value("nuevo.usuario@example.com"));
	}
	// Método utilitario para convertir un objeto a formato JSON
	private String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}