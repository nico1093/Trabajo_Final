package main.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import main.java.SEM.Entidades.Comercio;
import main.java.SEM.Entidades.Inspector;
import main.java.SEM.Estacionamiento.ZonaDeEstacionamiento;
import main.java.SEM.SEM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ComercioTestCase {
	private ZonaDeEstacionamiento zona;
	private Inspector inspector;
	private SEM sem;
	private Comercio comercio;
	
	
	@BeforeEach
	public void setUP() throws Exception {
		sem = SEM.getInstance();
		sem.reset();
		inspector = new Inspector(zona);
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		
	}

	@Test
	public void inicioDeComercio() {
		Assertions.assertTrue(zona.getComercios().contains(comercio));
	}

	@Test
	public void agregarEstacionamientoPorCompra() {
		comercio.generarEstacionamiento("ABC123", 2);
		Assertions.assertNotNull( sem.getEstacionamientos().get("ABC123") );
	}
	
	@Test
	public void agregarCompraPorRecarga() {
		int cantComprasGeneradas = sem.getCompras().size();
		comercio.recargarAplicativo(1564068646, 200);
		Assertions.assertTrue( sem.getCompras().size() > cantComprasGeneradas);
	}
	
	@Test
	public void testElComercioTieneUnID() {
		assertEquals(comercio.getId(), "Kiosco");
	}
	
	@Test
	public void testElComercioTieneUnaZona() {
		assertEquals(comercio.getZona(), zona);
	}
	
	@Test
	public void testNoPuedoRecargarSaldoNegativo() {
		comercio.recargarAplicativo(1153276390, -10);
		assertEquals(sem.saldoDeUsuario(1153276390), 0 ,0);
		assertTrue(sem.getCompras().isEmpty());
	}
	
	@Test
	public void testNoEstacionarPorHorasNegativas() {
		comercio.generarEstacionamiento("ABC 123", -10);
		assertTrue(sem.getCompras().isEmpty());
		assertTrue(sem.getEstacionamientos().isEmpty());
	}
}
