package pruebas;


import Controllers.ServicioDeAlerta;
import SEM.Aplication.App;
import SEM.Entidades.Comercio;
import SEM.Entidades.Inspector;
import SEM.Estacionamiento.Estacionamiento;
import SEM.Estacionamiento.ZonaDeEstacionamiento;
import SEM.SEM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SemTestCase {
	private ZonaDeEstacionamiento zona;
	private Inspector inspector;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private SEM sem;
	private Comercio comercio;
	private App app;

	@BeforeEach
	public void setUP() throws Exception {
		sem = SEM.getInstance();
		sem.reset();	
		inspector = new Inspector(zona);
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		System.setOut(new PrintStream(outContent));
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		app = new App(1153276406, "ABC123");
	}
	
	@Test
	public void seInicioUnEstacionamientoVirutal(){
		app.setUbicacionGPS(zona);
		comercio.recargarAplicativo(1153276406, 200);
		app.inicarEstacionamiento();
		Estacionamiento estacionamietno = zona.estacionamientoDe(app.getPatente());
		Assertions.assertTrue(sem.getEstacionamientos().containsKey(app.getPatente()));
		assertEquals(estacionamietno.getPatente(), app.getPatente() );
	}
	
	@Test
	public void seFinalizoUnEstacionamientoVirtual() {
		app.setUbicacionGPS(zona);
		comercio.recargarAplicativo(1153276406, 200);
		app.inicarEstacionamiento();
		app.finalizarEstacionamiento();
		Assertions.assertFalse( zona.estacionamientoDe(app.getPatente()).esVigente());
	}
	
	@Test
	public void agregarEstacionamientoPorCompra() {
		Assertions.assertTrue( sem.getCompras().isEmpty());
		comercio.generarEstacionamiento("ABC123", 2);
		Assertions.assertFalse( sem.getCompras().isEmpty() );
		Assertions.assertFalse( zona.getEstacionados().isEmpty() );
	}
	
	@Test
	public void agregarCompraPorRecarga() {
		Assertions.assertTrue( sem.getCompras().isEmpty());
		comercio.recargarAplicativo(1564068646, 200);
		Assertions.assertFalse( sem.getCompras().isEmpty());
	}
	
}
