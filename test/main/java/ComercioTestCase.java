package main.java;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import  org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ComercioTestCase {
	private ZonaDeEstacionamiento zona; 
	private Inspector inspector;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private SEM sem;
	private Comercio comercio;
	
	
	@BeforeEach
	public void setUP() throws Exception {
		sem = SEM.getInstance();
		sem.reset();
		inspector = new Inspector();
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		System.setOut(new PrintStream(outContent));
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		
	}
	
	@Test
	public void inicioDeComercio() {
		assertEquals(comercio.getId(), "Kiosco");
		assertEquals(comercio.getZona(), zona);
		assertTrue(zona.getComercios().contains(comercio));
	}
	
	@Test
	public void agregarEstacionamientoPorCompra() {
		assertTrue( sem.getCompras().isEmpty());
		comercio.generarEstacionamiento("ABC123", 2);
		assertFalse( sem.getCompras().isEmpty() );
		assertFalse( zona.getEstacionados().isEmpty() );
	}
	
	@Test
	public void agregarCompraPorRecarga() {
		assertTrue( sem.getCompras().isEmpty());
		comercio.recargarAplicativo(1564068646, 200);
		assertFalse( sem.getCompras().isEmpty());
	}
}
