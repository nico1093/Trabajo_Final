package main.java;


import main.java.Controllers.ServicioDeAlerta;
import main.java.SEM.Aplication.App;
import main.java.SEM.Aplication.IPantalla;
import main.java.SEM.Compras.Compra;
import main.java.SEM.Compras.CompraPorHoraPuntual;
import main.java.SEM.Compras.CompraPorRecarga;
import main.java.SEM.Entidades.Comercio;
import main.java.SEM.Entidades.Inspector;
import main.java.SEM.Estacionamiento.Estacionamiento;
import main.java.SEM.Estacionamiento.EstacionamientoPorCompra;
import main.java.SEM.Estacionamiento.EstacionamientoVirtual;
import main.java.SEM.Estacionamiento.ZonaDeEstacionamiento;
import main.java.SEM.SEM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;

public class SemTestCase {
	private ZonaDeEstacionamiento zona;
	private Inspector inspector;
	private SEM sem;
	private Comercio comercio;
	private App app;
	private IPantalla pantalla;
	private ServicioDeAlerta entidad;

	@BeforeEach
	public void setUP() throws Exception {
		sem = SEM.getInstance();
		sem.reset();
		sem.setPrecioPorHora( (double) 40 );	
		zona = mock(ZonaDeEstacionamiento.class);
		inspector = new Inspector(zona);
		//zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		pantalla = mock(IPantalla.class);
		entidad = mock(ServicioDeAlerta.class);
		app = new App(1153276406, "ABC123",pantalla);
	}
	
	@Test
	public void testAlIniciarceElSemTieneUnRegistroDeComprasVacia() {
		assertTrue(sem.getCompras().isEmpty());
	}
	
	@Test
	public void testAlIniciarceElSemTieneUnRegistroDeSaldosVacio() {
		assertTrue(sem.getRegistroSaldo().isEmpty());
	}

	@Test
	public void testAlIniciarceElSemTieneUnRegistroEstacionamientosVacio() {
		assertTrue(sem.getEstacionamientos().isEmpty());
	}
	
	@Test
	public void testAlIniciarceElSemTieneUnRegistroInfraccionesVacio() {
		assertTrue(sem.getInfracciones().isEmpty());
	}
	
	@Test
	public void testAlIniciarceElSemTieneUnRegistroDeEntidadesObservadorasVacio() {
		assertTrue(sem.getListeners().isEmpty());
	}
	
	@Test
	public void testSePuedeDefinirElPrecioDelEstacionamientoPorHora() {
		sem.setPrecioPorHora( (double) 50);
		assertEquals(sem.getPrecioPorHora(), 50, 0);
	}
	
	
	@Test
	public void agregarUnaCompraPorHoraPuntual() {
		comercio.generarEstacionamiento("ABC 123", 2);
		assertFalse(sem.getCompras().isEmpty());
	}
	
	@Test
	public void testAgregarUnaCompraPorRecarga() {
		comercio.recargarAplicativo(1153276406, 200);
		assertFalse(sem.getCompras().isEmpty());
	}
	
	@Test
	public void testAgregarUnEstacionamientoPorCompraALaZona() {
		comercio.generarEstacionamiento("ABC 123", 2);
		Mockito.verify(zona).agregarEstacionamiento(isA(EstacionamientoPorCompra.class));
	}
	
	@Test
	public void testAgregarUnEstacionamientoVirtualALaZona() {
		app.entroAZonaDeEstacionamiento(zona);
		comercio.recargarAplicativo(1153276406, 200);
		app.inicarEstacionamiento();
		Mockito.verify(zona).agregarEstacionamiento(isA(EstacionamientoVirtual.class));
	}
	
	@Test 
	public void alIniciarUnEstacionamientoSeNotificaALasEntidades() {
		sem.suscribirEntidad(entidad);
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		app.entroAZonaDeEstacionamiento(zona);
		comercio.recargarAplicativo(1153276406, 200);
		app.inicarEstacionamiento();
		comercio.generarEstacionamiento("ABC 123", 2);
		Mockito.verify(entidad, atLeast(2)).seInicioEstacionamiento(isA(Estacionamiento.class));
	}
	
	@Test
	public void testAlRealizarseUnaRecargaSeNotificaALasEntidades() {
		sem.suscribirEntidad(entidad);
		comercio.recargarAplicativo(1153276406, 200);
		Mockito.verify(entidad).seRealizoUnaRecarga(isA(CompraPorRecarga.class));
	}
	
	@Test
	public void testElPrimerNumeroDeCompraEs1() {
		comercio.recargarAplicativo(1153276406, 200);
		int numeroDeControl = sem.getCompras().get(0).getNroControl();
		assertEquals(numeroDeControl, 1);
	}
	
	@Test
	public void testElProximoNumeroDeCompraEs1() {
		comercio.recargarAplicativo(1153276406, 200);
		comercio.recargarAplicativo(1153276406, 200);
		int numeroDeControl = sem.getCompras().get(1).getNroControl();
		assertEquals(numeroDeControl, 2);
	}
	
	/*
	@Test
	public void testAlFinalizarUnEstacionamientoSeInformaALasEntidades() {
		sem.suscribirEntidad(entidad);
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		app.entroAZonaDeEstacionamiento(zona);
		comercio.recargarAplicativo(1153276406, 200);
		app.inicarEstacionamiento();
		app.finalizarEstacionamiento();
		Mockito.verify(entidad).seFinalizoEstacionamiento(isA(Estacionamiento.class));
	}
	*/

	@Test
	public void testAlCargarSaldoSeAgregaAlRegistro() {
		comercio.recargarAplicativo(1153276406, 200);
		assertEquals(sem.saldoDeUsuario(1153276406), 200, 0);
	}
	
	@Test
	public void testTieneSaldoSuficienteParaEstacionamiento() {
		comercio.recargarAplicativo(1153276406, 200);
		assertTrue( sem.tieneSaldoSuficiente(1153276406) );
	}
	
	@Test
	public void testNoTieneSaldoSuficienteParaEstacionamiento() {
		comercio.recargarAplicativo(1153276406, 20);
		assertFalse(sem.tieneSaldoSuficiente(1153276406));
	}
	
	@Test 
	public void testEstacionamientoEsValido() {
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		app.entroAZonaDeEstacionamiento(zona);
		comercio.recargarAplicativo(1153276406, 200);
		app.inicarEstacionamiento();
		assertTrue(sem.esValidoElEstacionamiento(app.getPatente()));
	}
	
	
	
	/*
	@Test
	public void seInicioUnEstacionamientoVirutal(){
		app.entroAZonaDeEstacionamiento(zona);
		comercio.recargarAplicativo(1153276406, 200);
		app.inicarEstacionamiento();
		Estacionamiento estacionamietno = zona.estacionamientoDe(app.getPatente());
		Assertions.assertTrue(sem.getEstacionamientos().containsKey(app.getPatente()));
		assertEquals(estacionamietno.getPatente(), app.getPatente() );
	}
	
	@Test
	public void seFinalizoUnEstacionamientoVirtual() {
		app.entroAZonaDeEstacionamiento(zona);
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
	
	*/
	
}
