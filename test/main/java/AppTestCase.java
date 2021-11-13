package main.java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

	
public class AppTestCase {
	
	private App app;
	
	
	@BeforeEach
	public void setUP() throws Exception {
		app = new App(1145648612, "AB 123 CD");
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
		app.driving();
		assertTrue(app.getEstado() instanceof EstadoConduciendo);
		app.walking();
		assertTrue(app.getEstado() instanceof EstadoCaminando);
	}
	
	
}
