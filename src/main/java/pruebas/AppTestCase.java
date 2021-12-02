package pruebas;



import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;


import Controllers.EstadoDeMovimiento;
import Controllers.IPantalla;
import Controllers.ModoDeApp;
import Controllers.ServicioDeAlerta;

import EstadosModos.Automatico;
import EstadosModos.EstadoCaminando;
import EstadosModos.EstadoConduciendo;
import EstadosModos.Manual;
import SEM.Aplication.*;
import SEM.Entidades.Comercio;
import SEM.Entidades.Inspector;
import SEM.Estacionamiento.ZonaDeEstacionamiento;
import SEM.SEM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.mockito.Mockito;


import static org.mockito.Mockito.mock;

	
public class AppTestCase {
	
	private App app;
	private ZonaDeEstacionamiento zona;
	private Inspector inspector;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private SEM sem;
	private Comercio comercio;
	private ServicioDeAlerta servicio;
	private IPantalla pantalla;
	
	
	
	@BeforeEach
	public void setUP() throws Exception {
		sem = SEM.getInstance();
		sem.reset();
		pantalla = mock(Controllers.IPantalla.class);
		app = new App(1145648612, "AB 123 CD", (Controllers.IPantalla) pantalla);
		inspector = mock(Inspector.class);
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		System.setOut(new PrintStream(outContent));
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		servicio =  mock(ServicioDeAlerta.class);
		sem.setPrecioPorHora(40.0);
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
		Assertions.assertEquals (app.saldoDeUsuario(), 200, 0);
	}
	
	
	@Test
	public void testNoSeIniciaEstacionamientoSiNoSeEntroEnUnaZona() {
		comercio.recargarAplicativo(1145648612, 200);
		app.inicarEstacionamiento();
		Assertions.assertFalse(app.isSeInicioEstacionamiento());
	}
	
	@Test
	public void testSiNoTieneCargaSeIniciaEstacionamiento() {
		app.entroAZonaDeEstacionamiento(zona);
		app.inicarEstacionamiento();
		Assertions.assertFalse(app.isSeInicioEstacionamiento());
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
		Assertions.assertTrue(app.isSeInicioEstacionamiento());
	}
	
	@Test
	public void testAlIniciarseElEstacionamientoSeMuestraEnPantalla() {
		comercio.recargarAplicativo(app.getNumero(), 1000);
		app.entroAZonaDeEstacionamiento(zona);
		app.inicarEstacionamiento();
		Mockito.verify(pantalla).mostrar("Hora de inicializacion: " + new Date().getHours() + ", Hora de finalizacion: " + "20"  );
	}
	
	@Test
	public void testAlIniciarseElEstacionamientoSeMuestaElMaximoDeHoras() {
		//Este test funciona dentro del horario
		comercio.recargarAplicativo(app.getNumero(), 40);
		app.entroAZonaDeEstacionamiento(zona);
		app.inicarEstacionamiento();
		Integer hora = new Date().getHours();
		Mockito.verify(pantalla).mostrar("Hora de inicializacion: " + hora + ", Hora de finalizacion: " + (hora + 1 ) );
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
		Assertions.assertTrue(app.isSeInicioEstacionamiento());
	}
	
	@Test
	public void alInciarEstacionamientoSeSacaSaldo() throws InterruptedException {
		comercio.recargarAplicativo(app.getNumero(), 200);
		app.entroAZonaDeEstacionamiento(zona);
		app.inicarEstacionamiento();
		Thread.sleep(1000);
		//Ejecuto la espera de un segundo para que la verificacion tenga el tiempo necesario para validar
		Assertions.assertEquals(app.saldoDeUsuario(), 160, 0);
	}
	
	@Test
	public void testFinalizarEstacionamiento() {
		app.entroAZonaDeEstacionamiento(zona);
		comercio.recargarAplicativo(1145648612, 200);
		app.inicarEstacionamiento();
		app.finalizarEstacionamiento();
		Assertions.assertFalse(app.isSeInicioEstacionamiento());
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
		Assertions.assertFalse(app.isSeInicioEstacionamiento());
	}
	
	
	
	/*@Test
	public void cambioDeModoEnAppTest() {
		assertTrue(app.getModo() instanceof Manual);
		app.cambiarModoAutomatico();
		assertTrue(app.getModo() instanceof Automatico);
		app.cambiarModoManual();
		assertTrue(app.getModo() instanceof Manual);
	}
	
	
	@Test
	public void cuandoLlegaElMensajeDrivingConduciendoNoCambiaDeEstado() {
		app.driving();
		assertTrue(app.getEstadoDeMovimiento() instanceof EstadoConduciendo);
		app.driving();
		assertTrue(app.getEstadoDeMovimiento() instanceof EstadoConduciendo);
	}
	
	@Test 
	public void cuandoLlegaElMensjeWalkingConducuiendoCambiaDeEstado() {
		app.entroAZonaDeEstacionamiento(zona);
		app.driving();
		assertTrue(app.getEstadoDeMovimiento() instanceof EstadoConduciendo);
		app.walking();
		assertTrue(app.getEstadoDeMovimiento() instanceof EstadoCaminando);
	}
	
	
	/*@Test
	public void laAppSeEncuentraEnUnaZonaDeEstacionamiento() {
		comercio.recargarAplicativo(1145648612, 200);
		app.setUbicacionGPS(zona);
		app.inicarEstacionamiento();
		Assertions.assertEquals(app.seEncuentraEnLaZonaEstacionamiento(), zona);
	}
	*/
	
	/*
	@Test
	public void laAppNoSeEncuentraEnUnaZonaDeEstacionamiento() {
		Assertions.assertNull(app.seEncuentraEnLaZonaEstacionamiento());
	}
	

	
	@Test
	public void alarmaDeInicioDeEstacionamiento() {
		app.entroAZonaDeEstacionamiento(zona);
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
		assertEquals (app.saldoDeUsuario(), 200, 0);
	}
	
	
	@Test
	public void iniciarEstacionamiento() throws InterruptedException {
		app.entroAZonaDeEstacionamiento(zona);
		comercio.recargarAplicativo(1145648612, 200);
		app.inicarEstacionamiento();
		assertTrue(app.isSeInicioEstacionamiento());
	}
	
	
	
	@Test
	public void finalizarEstacionamiento() {
		app.entroAZonaDeEstacionamiento(zona);
		comercio.recargarAplicativo(1145648612, 200);
		app.inicarEstacionamiento();
		app.finalizarEstacionamiento();
		assertFalse(app.isSeInicioEstacionamiento());
		assertEquals(app.saldoDeUsuario() ,160,0);
	}
	

	
	@Test
	public void finalizarEstacionamientoAutomatico() {
		app.cambiarModoAutomatico();
		comercio.recargarAplicativo(1145648612, 200);
		app.entroAZonaDeEstacionamiento(zona);
		app.driving();
		app.walking();
		app.finalizarEstacionamiento();
		Assertions.assertNull(sem.getEstacionamientos().get(app.getPatente()));
	}

	*/

}