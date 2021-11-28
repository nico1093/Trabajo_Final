package main.java;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntidadesCaseTest {
	
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
		System.setOut(new PrintStream(outContent));
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		entidad = new EntidadObservadora();
		sem.suscribirEntidad(entidad);
		app = new App(1153276406, "ABC123");
		
	}
	
	
	@Test
	public void seInicioUnEstacionamiento() {
		comercio.generarEstacionamiento("ABC123", 2);
		assertEquals(entidad.getContadorDeEstacionamientos(), 1);
	}
	
	
	
	@Test
	public void seFinalizoUnEstacionamiento(){
		app.setUbicacionGPS(zona);
		comercio.recargarAplicativo(1153276406, 200);
		app.inicarEstacionamiento();
		assertEquals( entidad.getContadorDeEstacionamientos(), 1 );
		app.finalizarEstacionamiento();
		assertEquals(entidad.getContadorDeEstacionamientos(), 0);
	}
	
	
}
