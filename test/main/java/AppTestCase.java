package main.java;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;

import main.java.Controllers.EstadoDeMovimiento;
import main.java.Controllers.ModoDeApp;
import main.java.Controllers.ServicioDeAlerta;
import main.java.EstadosModos.Automatico;
import main.java.EstadosModos.EstadoCaminando;
import main.java.EstadosModos.EstadoConduciendo;
import main.java.EstadosModos.Manual;
import main.java.SEM.Aplication.App;
import main.java.SEM.Aplication.IPantalla;
import main.java.SEM.Entidades.Comercio;
import main.java.SEM.Entidades.Inspector;
import main.java.SEM.Estacionamiento.ZonaDeEstacionamiento;
import main.java.SEM.SEM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
	
public class AppTestCase {
	
	private App app;
	private ZonaDeEstacionamiento zona;
	private Inspector inspector;
	private SEM sem;
	private Comercio comercio;
	private ServicioDeAlerta servicio;
	private IPantalla pantalla;
	
	
	
	@BeforeEach
	public void setUP() throws Exception {
		sem = SEM.getInstance();
		sem.reset();
		pantalla = mock(IPantalla.class);
		app = new App(1145648612, "AB 123 CD",pantalla);
		inspector = mock(Inspector.class);
		zona = new ZonaDeEstacionamiento(SEM.getInstance(),inspector, new ArrayList<Comercio>());
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		servicio =  mock(ServicioDeAlerta.class);
		sem.setPrecioPorHora((double) 40);
	}

	@Test
	public void testAppTieneUnNumeroAsociado() {
		assertEquals(app.getNumero(), 1145648612);
	}
	
	@Test 
	public void testAppTieneUnaPatenteAsociada() {
		assertEquals("AB 123 CD", app.getPatente());
	}
	
	@Test
	public void testAppTieneUnEstadoDeMovimiento() {
		assert(app.getEstadoDeMovimiento() instanceof EstadoDeMovimiento);
	}
	
	@Test
	public void testAlIniciarLaAppSuEstadoEsCaminando() {
		assert(app.getEstadoDeMovimiento() instanceof EstadoCaminando);
	}
	
	@Test
	public void testCambiarDeCaminandoAConduciendo() {
		app.driving();
		assert(app.getEstadoDeMovimiento() instanceof EstadoConduciendo);
	}
	
	@Test
	public void testCambiarDeConduciendoACaminando() {
		app.driving();
		app.walking();
		assert(app.getEstadoDeMovimiento() instanceof EstadoCaminando);
	}
	
	@Test
	public void testElEstadoCaminandoSeMantieneIgual() {
		app.walking();
		assert(app.getEstadoDeMovimiento() instanceof EstadoCaminando);
	}
	
	@Test
	public void testElEstadoConduciendoSeMantieneIgual() {
		app.driving();
		app.driving();
		assert(app.getEstadoDeMovimiento() instanceof EstadoConduciendo);
	}
	
	@Test
	public void testLaAppTieneUnModo() {
		assert(app.getModo() instanceof ModoDeApp);
	}
	
	@Test
	public void testAlIniciarLaAppEstaEnModoManual() {
		assert(app.getModo() instanceof Manual);
	}
	
	@Test
	public void testCambiarEstadoDeManualAutomatico() {
		app.cambiarModoAutomatico();
		assert(app.getModo() instanceof Automatico);
	}
	
	@Test
	public void testCambiarEstadoDeAutomaticoAManual() {
		app.cambiarModoAutomatico();
		app.cambiarModoManual();
		assert(app.getModo() instanceof Manual);
	}
	
	@Test
	public void testElModoManualNoCambia() {
		app.cambiarModoManual();
		assert(app.getModo() instanceof Manual);
	}
	
	@Test
	public void testElModoAutomaticoNoCambia() {
		app.cambiarModoAutomatico();
		app.cambiarModoAutomatico();
		assert(app.getModo() instanceof Automatico);
	}
	
	
	@Test 
	public void testobtenerSaldo() {
		comercio.recargarAplicativo(1145648612, 200);
		assertEquals (app.saldoDeUsuario(), 200, 0);
	}
	
	
	@Test
	public void testNoSeIniciaEstacionamientoSiNoSeEntroEnUnaZona() {
		comercio.recargarAplicativo(1145648612, 200);
		app.inicarEstacionamiento();
		assertFalse(app.isSeInicioEstacionamiento());
	}
	
	@Test
	public void testSiNoTieneCargaSeIniciaEstacionamiento() {
		app.entroAZonaDeEstacionamiento(zona);
		app.inicarEstacionamiento();
		assertFalse(app.isSeInicioEstacionamiento());
	}
	
	@Test
	public void testSiNoTieneCargaSeMuestraEnPantalla() {
		app.entroAZonaDeEstacionamiento(zona);
		app.inicarEstacionamiento();
		Mockito.verify(pantalla).mostrar("saldo insuficiente. SEM.Estacionamiento no permitido");
	}
	
	@Test
	public void testSeIniciaEstacionamiento() {
		comercio.recargarAplicativo(app.getNumero(), 200);
		app.entroAZonaDeEstacionamiento(zona);
		app.inicarEstacionamiento();
		assertTrue(app.isSeInicioEstacionamiento());
	}
	
	@Test
	public void testAlIniciarseElEstacionamientoSeMuestraEnPantalla() {
		comercio.recargarAplicativo(app.getNumero(), 1000);
		app.entroAZonaDeEstacionamiento(zona);
		app.inicarEstacionamiento();
		Mockito.verify(pantalla).mostrar("Hora de inicializacion: " + new Date().getHours() + ", Hora de finalizacion: " + "20"  );
	}

	
	
	@Test
	public void testalarmaDeInicioDeEstacionamiento() {
		app.entroAZonaDeEstacionamiento(zona);
		app.driving();
		app.walking();
		Mockito.verify(pantalla).mostrar("No se inicio un estacionamiento");;
	}

	
	@Test
	public void testAlarmaDeEstacionamientoNoFinalizado() {
		comercio.recargarAplicativo(app.getNumero(), 40);
		app.entroAZonaDeEstacionamiento(zona);
		app.inicarEstacionamiento();
		app.driving();
		Mockito.verify(pantalla).mostrar("No se finalizo el estacionamiento");
	}
	
	@Test
	public void testIniciarEstacionamientoAutomatico() {
		app.cambiarModoAutomatico();
		comercio.recargarAplicativo(1145648612, 300);
		app.entroAZonaDeEstacionamiento(zona);
		app.driving();
		app.walking();
		assertTrue(app.isSeInicioEstacionamiento());
	}
	
	@Test
	public void alInciarEstacionamientoSeSacaSaldo() throws InterruptedException {
		comercio.recargarAplicativo(app.getNumero(), 200);
		app.entroAZonaDeEstacionamiento(zona);
		app.inicarEstacionamiento();
		assertEquals(app.saldoDeUsuario(), 160, 0);
	}
	
	@Test
	public void testFinalizarEstacionamiento() {
		app.entroAZonaDeEstacionamiento(zona);
		comercio.recargarAplicativo(1145648612, 200);
		app.inicarEstacionamiento();
		app.finalizarEstacionamiento();
		assertFalse(app.isSeInicioEstacionamiento());
	}
	
	@Test
	public void testAlFinalizarEstacionamientoSeMuestraEnPantalla() {
		app.entroAZonaDeEstacionamiento(zona);
		comercio.recargarAplicativo(1145648612, 200);
		app.inicarEstacionamiento();
		app.finalizarEstacionamiento();
		Mockito.verify(pantalla).mostrar("Hora de iniciacion: " + new Date().getHours() + 
				", Hora de finalizacion: " + new Date().getHours() + 
				", Horas totales de estacionamiento: " +  0 +
				", Costo de estacionamiento: " + 40.0 );
	}
	
	@Test
	public void testFinalizarEstacionamientoAutomatico() {
		app.cambiarModoAutomatico();
		comercio.recargarAplicativo(1145648612, 200);
		app.entroAZonaDeEstacionamiento(zona);
		app.driving();
		app.walking();
		app.finalizarEstacionamiento();
		assertFalse(app.isSeInicioEstacionamiento());
	}
	


}