package pruebas;


import java.util.ArrayList;

import SEM.Entidades.Comercio;
import SEM.Entidades.Inspector;
import SEM.Estacionamiento.ZonaDeEstacionamiento;
import SEM.SEM;
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
		Assertions.assertEquals(comercio.getId(), "Kiosco");
	}
	
	@Test
	public void testElComercioTieneUnaZona() {
		Assertions.assertEquals(comercio.getZona(), zona);
	}
}
