package main.java;

public class CompraPorHoraPuntual extends Compra {
	private int HorasCompradas;
	private String patente;

	public CompraPorHoraPuntual(int horasCompradas, int nroControl, Comercio comercio) {
		super(nroControl, comercio);
		HorasCompradas = horasCompradas;
	}
	
	

}
