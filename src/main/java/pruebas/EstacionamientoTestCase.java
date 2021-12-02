package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import SEM.Estacionamiento.EstacionamientoVirtual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class EstacionamientoTestCase {
	
	private EstacionamientoVirtual estacionamientoVirtual;
	
	@BeforeEach
	public void setUp() {
		estacionamientoVirtual = new EstacionamientoVirtual("ABC 123", 1153276406);
	}
	
	@Test
	public void testElEstacionamientoVirtualTieneUnNumeroAsociado() {
		assertEquals(estacionamientoVirtual.getPhone(), 1153276406);
	}

}
