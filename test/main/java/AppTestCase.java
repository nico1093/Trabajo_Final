package main.java;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import main.java.EstadosModos.Automatico;
import main.java.EstadosModos.EstadoCaminando;
import main.java.EstadosModos.EstadoConduciendo;
import main.java.EstadosModos.Manual;
import main.java.SEM.SEM;
import main.java.SEM.Aplication.App;
import main.java.SEM.Entidades.Comercio;
import main.java.SEM.Entidades.Inspector;
import main.java.SEM.Estacionamiento.ZonaDeEstacionamiento;
import sem.app.EstadoDeMovimiento;

	
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
		inspector =  mock(Inspector.class);
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		System.setOut(new PrintStream(outContent));
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		
	}
	
	
	@Test
	public void testAppTieneUnNumeroAsociado() {
		assertEquals(app.getNumero(), 1145325967);
	}
	
	@Test 
	public void testAppTieneUnaPatenteAsociada() {
		assertEquals("ABC123", app.getPatente());
	}
	
	@Test
	public void testAppTieneUnEstadoDeMovimiento() {
		assert(app.getEstadoDeMovimiento() instanceof main.java.Controllers.EstadoDeMovimiento);
	}
	
	@Test
	public void alIniciarseLaAppSuEstadoEsCaminando() {
		assert(app.getEstadoDeMovimiento().estaCaminando());
	}
	
	@Test
	public void alLlegarElMensajeDrivingLaAppCambiaDeEstado() {
		app.driving();
		assert(app.getEstadoDeMovimiento().estaConduciendo());
	}
	
	@Test
	public void alLlegarElMensajeWalkingCaminandoLaAppNoCambiaDeEstado() {
		app.walking();
		assert(app.getEstadoDeMovimiento().estaCaminando());
	}
	
	@Test
	public void alLlegarElMensajeWalkingConduciendoLaAppCambiaDeEstado() {
		app.driving();
		app.walking();
		assert(app.getEstadoDeMovimiento().estaCaminando());
	}
	
	@Test
	public void alLlegarElMensajeDrivingConduciendoLaAppNoCambiaDeEstado() {
		app.driving();
		app.driving();
		assert(app.getEstadoDeMovimiento().estaConduciendo());
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
	public void alarmaDeInicioDeEstacionamiento() {
		app.setUbicacionGPS(zona);
		app.driving();
		app.walking();
		assertEquals("No se inicio el estacionamiento", outContent.toString());
	}
	
	@Test
	public void mensajeDeUsuarioSinCredito() {
		app.inicarEstacionamiento();
		assertEquals("saldo insuficiente. SEM.Estacionamiento no permitido", outContent.toString());
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