package main.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import main.java.SEM.SEM;

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
		sem.reset();	
		
		inspector = new Inspector();
		
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		
		inspector.setZonaEncargada(zona);
		
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		
		app = new App(1145648612, "AB 123 CD");
	}
	

	@Test
	public void inspectorIncial() {
		sem = SEM.getInstance();
		sem.reset();	
		inspector = new Inspector();
		assertEquals(inspector.getZonaEncargada(),zona);
		assertEquals(inspector.getSem(), sem );
	}
	
	@Test
	public void inspectorInspeccionaUnaPatente() {
		sem = SEM.getInstance();
		sem.reset();	
		
		inspector = new Inspector();
		
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		
		inspector.setZonaEncargada(zona);
		
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		
		app = new App(1145648612, "AB 123 CD");
		app.setUbicacionGPS(zona);
		comercio.recargarAplicativo(1145648612, 200);
		app.inicarEstacionamiento();
		app.finalizarEstacionamiento();
		inspector.inspeccionarEstacionamiento("AB 123 CD");
		assertFalse(sem.getInfracciones().isEmpty());
	}
}
