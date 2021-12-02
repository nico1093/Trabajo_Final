package pruebas;


import Controllers.IPantalla;
import Controllers.ServicioDeAlerta;
import SEM.Aplication.App;
import SEM.Compras.CompraPorRecarga;
import SEM.Entidades.Comercio;
import SEM.Entidades.Inspector;
import SEM.Estacionamiento.Estacionamiento;
import SEM.Estacionamiento.EstacionamientoPorCompra;
import SEM.Estacionamiento.EstacionamientoVirtual;
import SEM.Estacionamiento.ZonaDeEstacionamiento;
import SEM.SEM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
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
		Assertions.assertTrue(sem.getCompras().isEmpty());
	}
	
	@Test
	public void testAlIniciarceElSemTieneUnRegistroDeSaldosVacio() {
		Assertions.assertTrue(sem.getRegistroSaldo().isEmpty());
	}

	@Test
	public void testAlIniciarceElSemTieneUnRegistroEstacionamientosVacio() {
		Assertions.assertTrue(sem.getEstacionamientos().isEmpty());
	}
	
	@Test
	public void testAlIniciarceElSemTieneUnRegistroInfraccionesVacio() {
		Assertions.assertTrue(sem.getInfracciones().isEmpty());
	}
	
	@Test
	public void testAlIniciarceElSemTieneUnRegistroDeEntidadesObservadorasVacio() {
		Assertions.assertTrue(sem.getListeners().isEmpty());
	}
	
	@Test
	public void testSePuedeDefinirElPrecioDelEstacionamientoPorHora() {
		sem.setPrecioPorHora(50.0);
		assertEquals(sem.getPrecioPorHora(), 50, 0);
	}
	
	
	@Test
	public void agregarUnaCompraPorHoraPuntual() {
		comercio.generarEstacionamiento("ABC 123", 2);
		Assertions.assertFalse(sem.getCompras().isEmpty());
	}
	
	@Test
	public void testAgregarUnaCompraPorRecarga() {
		comercio.recargarAplicativo(1153276406, 200);
		Assertions.assertFalse(sem.getCompras().isEmpty());
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
		Assertions.assertTrue( sem.tieneSaldoSuficiente(1153276406) );
	}
	
	@Test
	public void testNoTieneSaldoSuficienteParaEstacionamiento() {
		comercio.recargarAplicativo(1153276406, 20);
		Assertions.assertFalse(sem.tieneSaldoSuficiente(1153276406));
	}
	
	@Test 
	public void testEstacionamientoEsValido() {
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		app.entroAZonaDeEstacionamiento(zona);
		comercio.recargarAplicativo(1153276406, 200);
		app.inicarEstacionamiento();
		Assertions.assertTrue(sem.esValidoElEstacionamiento(app.getPatente()));
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
