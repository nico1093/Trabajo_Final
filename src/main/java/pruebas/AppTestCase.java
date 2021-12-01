package pruebas;


import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import Controllers.ServicioDeAlerta;
import EstadosModos.Automatico;
import EstadosModos.EstadoCaminando;
import EstadosModos.EstadoConduciendo;
import EstadosModos.Manual;
import SEM.Aplication.App;
import SEM.Entidades.Comercio;
import SEM.Entidades.Inspector;
import SEM.Estacionamiento.ZonaDeEstacionamiento;
import SEM.SEM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
	
public class AppTestCase {
	
	private App app;
	private ZonaDeEstacionamiento zona;
	private Inspector inspector;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private SEM sem;
	private Comercio comercio;
	private ServicioDeAlerta servicio;
	
	
	
	@BeforeEach
	public void setUP() throws Exception {
		sem = SEM.getInstance();
		sem.reset();
		app = new App(1145648612, "AB 123 CD");
		inspector = new Inspector(zona);
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		System.setOut(new PrintStream(outContent));
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		servicio =  mock(ServicioDeAlerta.class);
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
		comercio.recargarAplicativo(1145648612, 200);
		app.setUbicacionGPS(zona);
		app.inicarEstacionamiento();
		Assertions.assertEquals(app.seEncuentraEnLaZonaEstacionamiento(), zona);
	}
	
	@Test
	public void laAppNoSeEncuentraEnUnaZonaDeEstacionamiento() {
		Assertions.assertNull(app.seEncuentraEnLaZonaEstacionamiento());
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
	public void iniciarEstacionamiento() throws InterruptedException {
		app.setUbicacionGPS(zona);
		comercio.recargarAplicativo(1145648612, 200);
		app.inicarEstacionamiento();
		assertTrue(app.isSeInicioEstacionamiento());
	}
	
	@Test
	public void finalizarEstacionamiento() {
		app.setUbicacionGPS(zona);
		comercio.recargarAplicativo(1145648612, 200);
		app.inicarEstacionamiento();
		app.finalizarEstacionamiento();
		assertFalse(app.isSeInicioEstacionamiento());
		//assertEquals(app.saldoDeUsuario() ,160);
	}
	
	@Test
	public void iniciarEstacionamientoAutomatico() {
		app.cambiarModoAutomatico();
		comercio.recargarAplicativo(1145648612, 300);
		app.setUbicacionGPS(zona);
		app.driving();
		app.walking();
		assertTrue(app.isSeInicioEstacionamiento());
	}
	
	@Test
	public void finalizarEstacionamientoAutomatico() {
		app.cambiarModoAutomatico();
		comercio.recargarAplicativo(1145648612, 200);
		app.setUbicacionGPS(zona);
		app.driving();
		app.walking();
		app.finalizarEstacionamiento();
		Assertions.assertNull(sem.getEstacionamientos().get(app.getPatente()));
	}

	@Test
	public void validacionMensajeSaldoInsufuciente(){
		servicio.mensajeDeAlertaAlUsuario("Su saldo es insuficiente para iniciar el estacionamiento");
		Mockito.verify(servicio).mensajeDeAlertaAlUsuario("Su saldo es insuficiente para iniciar el estacionamiento");
	}

	@Test
	public void validacionMensajeInicioDeEstacionamiento(){
		servicio.mensajeDeAlertaAlUsuario("Se ha registrado correctamente su estacionamiento.");
		Mockito.verify(servicio).mensajeDeAlertaAlUsuario("Se ha registrado correctamente su estacionamiento.");
	}

	@Test
	public void validacionMensajeFinalizacionDeEstacionamiento(){
		servicio.mensajeDeAlertaAlUsuario("Usted ha finalizado su estacionamiento. Tenga buen dia.");
		Mockito.verify(servicio).mensajeDeAlertaAlUsuario("Usted ha finalizado su estacionamiento. Tenga buen dia.");
	}

	@Test
	public void validacionMensajeFalloDeInicio(){
		servicio.mensajeDeAlertaAlUsuario("Ah surgido un error al inicio de su estacionamiento. Intente nuevamente.");
		Mockito.verify(servicio).mensajeDeAlertaAlUsuario("Ah surgido un error al inicio de su estacionamiento. Intente nuevamente.");
	}

}