package main.java;


import static org.mockito.Mockito.*;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

	
public class AppTestCase {
	
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

	@Test
	public void testCuandoUnaAppSeInicia() {
		assertEquals(app.getNumero(), 1145648612);
		assertEquals(app.getPatente(), "AB 123 CD");
		assertFalse(app.isSeInicioEstacionamiento());
		assertTrue(app.getModo() instanceof Manual);
		assertTrue(app.getEstado() instanceof EstadoCaminando);
	}
	
	@Test
	public void cambioDeModoEnAppTest() {
		assertTrue(app.getModo() instanceof Manual);
		app.cambiarModoAutomatico();
		assertTrue(app.getModo() instanceof Automatico);
		app.cambiarModoManual();
		assertTrue(app.getModo() instanceof Manual);
	}
	
	@Test
	public void cuandoLlegaElMensajeDrivinCaminandoCambiaDeEstado() {
		assertTrue(app.getEstado() instanceof EstadoCaminando);
		app.driving();
		assertTrue(app.getEstado() instanceof EstadoConduciendo);
	}
	
	@Test
	public void cuandoLlegaElMensjeWalkingCaminandoNoCambiaDeEstado() {
		assertTrue(app.getEstado() instanceof EstadoCaminando);
		app.walking();
		assertTrue(app.getEstado() instanceof EstadoCaminando);
	}
	
	@Test
	public void cuandoLlegaElMensajeDrivingConduciendoNoCambiaDeEstado() {
		app.driving();
		assertTrue(app.getEstado() instanceof EstadoConduciendo);
		app.driving();
		assertTrue(app.getEstado() instanceof EstadoConduciendo);
	}
	
	@Test 
	public void cuandoLlegaElMensjeWalkingConducuiendoCambiaDeEstado() {
		app.setUbicacionGPS(zona);
		app.driving();
		assertTrue(app.getEstado() instanceof EstadoConduciendo);
		app.walking();
		assertTrue(app.getEstado() instanceof EstadoCaminando);
	}
	
	
	@Test
	public void laAppSeEncuentraEnUnaZonaDeEstacionamiento() {
		app.setUbicacionGPS(zona);
		assertTrue(app.seEncuentraEnUnaZonaEstacionamiento());
	}
	
	@Test
	public void laAppNoSeEncuentraEnUnaZonaDeEstacionamiento() {
		assertFalse(app.seEncuentraEnUnaZonaEstacionamiento());
	}

	
	@Test
	public void alarmaDeInicioDeEstacionamiento() {
		app.setUbicacionGPS(zona);
		app.driving();
		app.walking();
		assertEquals("No se inicio el estacionamiento", outContent.toString());
	}
	
	@Test
	public void mensajeDeUsuarioSinCredito() {
		app.inicarEstacionamiento();
		assertEquals("saldo insuficiente. Estacionamiento no permitido", outContent.toString());
	}
	
	@Test 
	public void obtenerSaldo() {
		comercio.recargarAplicativo(1145648612, 200);
		assertEquals(app.saldoDeUsuario(), (double) 200);
	}
	
	
	@Test
	public void iniciarEstacionamiento() {
		app.setUbicacionGPS(zona);
		comercio.recargarAplicativo(1145648612, 200);
		app.inicarEstacionamiento();
		assertTrue(app.isSeInicioEstacionamiento());
		assertEquals(app.saldoDeUsuario() ,160);
	}
	
	@Test
	public void finalizarEstacionamiento() {
		app.setUbicacionGPS(zona);
		comercio.recargarAplicativo(1145648612, 200);
		app.inicarEstacionamiento();
		app.finalizarEstacionamiento();
		assertFalse(app.isSeInicioEstacionamiento());
		assertEquals(app.saldoDeUsuario() ,160);
	}
	
	@Test
	public void iniciarEstacionamientoAutomatico() {
		sem.reset();
		app.cambiarModoAutomatico();
		comercio.recargarAplicativo(1145648612, 300);
		app.setUbicacionGPS(zona);
		app.driving();
		app.walking();
		assertTrue(app.isSeInicioEstacionamiento());
		assertEquals(app.saldoDeUsuario() ,260);
	}
	
	@Test
	public void finalizarEstacionamientoAutomatico() {
		app.cambiarModoAutomatico();
		comercio.recargarAplicativo(1145648612, 200);
		app.setUbicacionGPS(zona);
		app.driving();
		app.walking();
		assertTrue(app.isSeInicioEstacionamiento());
		assertEquals(app.saldoDeUsuario() ,160);
	}
	
	
	
	

	
}