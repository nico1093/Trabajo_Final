package SEM.Compras;

import SEM.Entidades.Comercio;

public class CompraPorRecarga extends Compra {


	private int numero;
	private double monto;
	
	public int getNumero() {
		return numero;
	}
	
	
	public double getMonto() {
		return monto;
	}

	public CompraPorRecarga(int nroControl, Comercio comercio, int numero, double monto) {
		super(nroControl, comercio);
		this.numero = numero;
		this.monto = monto;
	}
	
	
}
