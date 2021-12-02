package main.java;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Date;

import main.java.SEM.Compras.CompraPorHoraPuntual;
import main.java.SEM.Compras.CompraPorRecarga;
import main.java.SEM.Entidades.Comercio;
import main.java.SEM.Entidades.Inspector;
import main.java.SEM.Estacionamiento.ZonaDeEstacionamiento;
import main.java.SEM.SEM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CompraTestCase {

	private CompraPorHoraPuntual compraPorHora;
	private ZonaDeEstacionamiento zona;
	private Inspector inspector;
	private Comercio comercio;
	private CompraPorRecarga compraPorRecarga;
	private SEM sem;
	
	
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
	public void testUnaCompraTieneUnNumeroDeControlAlEntrarEnElSem() {
		comercio.generarEstacionamiento("ABC 123 ", 2);
		comercio.recargarAplicativo(1145648612, 50);
		assertEquals(sem.getCompras().get(0).getNroControl(), 1);
		assertEquals(sem.getCompras().get(1).getNroControl(), 2);
	}
	
	@Test
	public void testUnaCompraTieneAsociadaElComercioDondeSeHizo() {
		comercio.generarEstacionamiento("ABC 123 ", 2);
		assertEquals(sem.getCompras().get(0).getComercio() , comercio );
	}
	
	@Test
	public void testUnaCompraTieneAsociadoLaFechaEnQueSeHizo() {
		comercio.generarEstacionamiento("AAS 142", 3);
		assertTrue(sem.getCompras().get(0).getFecha() instanceof Date);
	}
	
	
	
}
