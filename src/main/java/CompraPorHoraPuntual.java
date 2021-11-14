package main.java;

public class CompraPorHoraPuntual extends Compra {
	private int HorasCompradas;

	public CompraPorHoraPuntual(int horasCompradas, int nroControl, Comercio comercio) {
		super(nroControl, comercio);
		HorasCompradas = horasCompradas;
	}

	public int getHorasCompradas() {
		return HorasCompradas;
	}


	
	

}
