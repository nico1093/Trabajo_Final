package main.java;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import main.java.SEM.SEM;

public class ZonaDeEstacionamientoTestCase {
	
	private App app;
	private ZonaDeEstacionamiento zona; 
	private Inspector inspector;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private SEM sem;
	private Comercio comercio;
	
	
	
	@BeforeEach
	public void setUP() throws Exception {
		sem = SEM.getInstance();
		sem.reset();
		app = new App(1145648612, "AB 123 CD");
		inspector = new Inspector();
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		System.setOut(new PrintStream(outContent));
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
	}
	
	@Test public void inicioDeZonaDeEstacionamiento() {
		assertTrue(zona.getComercios().contains(comercio));
		
	}

}
