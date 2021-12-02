package main.java;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.PrintStream;
import java.util.ArrayList;

import main.java.SEM.Aplication.App;
import main.java.SEM.Aplication.IPantalla;
import main.java.SEM.Entidades.Comercio;
import main.java.SEM.Entidades.Inspector;
import main.java.SEM.Estacionamiento.ZonaDeEstacionamiento;
import main.java.SEM.SEM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ZonaDeEstacionamientoTestCase {
	
	private App app;
	private ZonaDeEstacionamiento zona;
	private Inspector inspector;
	private SEM sem;
	private Comercio comercio;
	private IPantalla pantalla;
	
	
	
	@BeforeEach
	public void setUP() throws Exception {
		sem = SEM.getInstance();
		sem.reset();
		pantalla = mock(IPantalla.class);
		app = new App(1145648612, "AB 123 CD",pantalla);
		inspector = new Inspector(zona);
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
	}
	
	@Test
	public void inicioDeZonaDeEstacionamiento() {
		Assertions.assertTrue(zona.getComercios().contains(comercio));
		
	}
	
	@Test
	public void testLaZonaDeEstacionamientoTieneAlSemAsociado() {
		assertEquals(zona.getSem(), sem);
	}
	
	@Test
	public void testLaZonaTieneUnInspectorAsociado() {
		assertEquals(zona.getEncargado(), inspector);
	}
	

}
