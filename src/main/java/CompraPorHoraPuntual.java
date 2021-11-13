package main.java;

public class CompraPorHoraPuntual extends Compra {
	private int HorasCompradas;

	public CompraPorHoraPuntual(int horasCompradas, int nroControl, String patente, Comercio comercio) {
		super(nroControl, patente, comercio);
		HorasCompradas = horasCompradas;
	}
	
	

}
