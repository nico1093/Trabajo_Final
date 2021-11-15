package main.java;

import java.util.ArrayList;
import java.util.List;

public class EntidadObservadora implements ServicioDeAlerta {
	
	private int contadorDeEstacionamientos = 0;
	private int contadorDeCompras = 0;
	
	public EntidadObservadora() {
		super();
	}

	@Override
	public void seInicioEstacionamiento(Estacionamiento estacionamiento) {
		this.setContadorDeEstacionamientos(this.getContadorDeEstacionamientos() + 1);
	}


	@Override
	public void seFinalizoEstacionamiento(Estacionamiento estacionamiento) {
		this.setContadorDeEstacionamientos(this.getContadorDeEstacionamientos() + 1);;
	}

	public int getContadorDeEstacionamientos() {
		return contadorDeEstacionamientos;
	}

	@Override
	public void seRealizoUnaRecarga(Compra compra) {
		this.setContadorDeCompras(this.getContadorDeCompras() + 1 ) ;

	}

	public void setContadorDeEstacionamientos(int contadorDeEstacionamientos) {
		this.contadorDeEstacionamientos = contadorDeEstacionamientos;
	}

	public void setContadorDeCompras(int contadorDeCompras) {
		this.contadorDeCompras = contadorDeCompras;
	}

	public int getContadorDeCompras() {
		return contadorDeCompras;
	}


}
