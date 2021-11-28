package pruebas;



import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import Controllers.EntidadObservadora;
import SEM.Aplication.App;
import SEM.Entidades.Comercio;
import SEM.Entidades.Inspector;
import SEM.Estacionamiento.Estacionamiento;
import SEM.Estacionamiento.ZonaDeEstacionamiento;
import SEM.SEM;
import SEM.Infraccion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InspectorTestCase {
	private ZonaDeEstacionamiento zona;
	private Inspector inspector;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private SEM sem;
	private Comercio comercio;
	private EntidadObservadora entidad;
	private App app;
	
	
	@BeforeEach
	public void setUP() throws Exception {
		sem = SEM.getInstance();
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		app = new App(1145648612, "AB 123 CD");
		comercio.recargarAplicativo(1145648612, 2000);
	}
	

	@Test
	public void inspectorIncial() {
		Inspector encargado = new Inspector(zona);
		Assertions.assertEquals(encargado.getZonaEncargada(),zona);
	}

	@Test
	public void inspectorInspeccionaUnaPatente() {
		Inspector encargado = new Inspector(zona);
		Estacionamiento hospedado = null;
		app.setUbicacionGPS(zona);
		app.inicarEstacionamiento();
		for(Estacionamiento estacionado : sem.getEstacionamientos().get(app.getPatente()).getEstacionados()){
			if(estacionado.getPatente().equals(app.getPatente())){
				hospedado = estacionado;
			}
		}
		hospedado.anularValidez();
		encargado.inspeccionarEstacionamiento(app.getPatente());
		Assertions.assertTrue(sem.getInfracciones().size() > 0);
	}
}