package main.java;

public class CompraPorRecarga extends Compra {
	
	private int numero;
	private double monto;
	
    public CompraPorRecarga(int nroControl, String patente, Comercio comercio, int numero, double monto){
    	super(nroControl, patente, comercio);
    	this.numero=numero;
    	this.monto= monto;
    	
    }

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}
    
    
	
}	

