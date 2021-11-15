package main.java;

import java.util.ArrayList;
import java.util.List;

public class EntidadObservadora implements ServicioDeAlerta {
	
	private int contadorDeEstacionamientos = 0;
	
	public EntidadObservadora() {
		super();
	}

	@Override
	public void seInicioEstacionamiento(Estacionamiento estacionamiento) {
		this.contadorDeEstacionamientos ++;
	}



	@Override
	public void seFinalizoEstacionamiento(Estacionamiento estacionamiento) {
		this.contadorDeEstacionamientos --;
	}

	public int getContadorDeEstacionamientos() {
		return contadorDeEstacionamientos;
	}

	@Override
	public void seRealizoUnaRecarga(Compra compra) {
		this.getCompras().add(compra);

	}

	public List<Compra> getCompras() {
		return compras;
	}

	public List<Estacionamiento> getEstacionamientos() {
		return estacionamientos;
	}
}
