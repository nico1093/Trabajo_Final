package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

import SEM.Compras.CompraPorHoraPuntual;
import SEM.Compras.CompraPorRecarga;
import SEM.Entidades.Comercio;
import SEM.Entidades.Inspector;
import SEM.Estacionamiento.ZonaDeEstacionamiento;
import SEM.SEM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CompraTestCase {

	private CompraPorHoraPuntual compraPorHora;
	private ZonaDeEstacionamiento zona;
	private Inspector inspector;
	private Comercio comercio;
	private CompraPorRecarga compraPorRecarga;
	
	
	@BeforeEach
	public void setUP() throws Exception {
		inspector = new Inspector(zona);
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
	}

	@Test
	public void testCuandoUnaCompraPorRecarga() {
		compraPorRecarga = new CompraPorRecarga(0, comercio, 1153276406, 200);
		Assertions.assertEquals(compraPorRecarga.getMonto(), 200);

	}

	@Test
	public void testCuandoUnaCompraPorHorasFijasSeInicia() {
		compraPorHora = new CompraPorHoraPuntual(2, 0, comercio);
		assertEquals(2, compraPorHora.getHorasCompradas());
	}
	
}
