package main.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.SEM.SEM;
import main.java.SEM.Compras.CompraPorHoraPuntual;
import main.java.SEM.Compras.CompraPorRecarga;
import main.java.SEM.Entidades.Comercio;
import main.java.SEM.Entidades.Inspector;
import main.java.SEM.Estacionamiento.ZonaDeEstacionamiento;

public class CompraPorHoraPuntualTestCase {
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
	public void testCuandoUnaCompraPorHorasFijasSeInicia() {
		compraPorHora = new CompraPorHoraPuntual(2, 0, comercio);
		assertEquals(2, compraPorHora.getHorasCompradas());
	}
	
}
