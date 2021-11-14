package main.java;

public class CompraPorRecarga extends Compra {
	private int numero;
	private double monto;
	

	public CompraPorRecarga(int nroControl, Comercio comercio, int numero, double monto) {
		super(nroControl, comercio);
		this.numero = numero;
		this.monto = monto;
	}
	
	
}
