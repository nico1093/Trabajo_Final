package SEM.Estacionamiento;

import SEM.Entidades.Comercio;
import SEM.Entidades.Inspector;
import SEM.SEM;

import java.util.ArrayList;
import java.util.List;

public class ZonaDeEstacionamiento {
    private SEM sem = SEM.getInstance();
    private Inspector encargado;
    private List<Comercio> comercios;
    private List<Estacionamiento> estacionados;
    
    
    
    
	public ZonaDeEstacionamiento(SEM sem, Inspector encargado, List<Comercio> comercios) {
		super();
		this.sem = sem;
		this.encargado = encargado;
		this.comercios = comercios;
		this.estacionados = new ArrayList<Estacionamiento>();
	}




	public void agregarEstacionamiento(Estacionamiento estacionamiento) {
		this.getEstacionados().add(estacionamiento);
		
	}




	public SEM getSem() {
		return sem;
	}




	public void setSem(SEM sem) {
		this.sem = sem;
	}




	public Inspector getEncargado() {
		return encargado;
	}




	public void setEncargado(Inspector encargado) {
		this.encargado = encargado;
	}




	public List<Comercio> getComercios() {
		return comercios;
	}




	public void setComercios(List<Comercio> comercios) {
		this.comercios = comercios;
	}




	public List<Estacionamiento> getEstacionados() {
		return estacionados;
	}








	public boolean estacionamientoEsVigente(String patente) {
		Estacionamiento resultado = null;
		for (Estacionamiento estacionamiento : this.getEstacionados()) {
	        if (estacionamiento.getPatente().equals(patente)) {
	            resultado = estacionamiento;
	        }
		}
		return resultado.esVigente();
	}
	

	public Estacionamiento estacionamientoDe(String patente) {
		for (Estacionamiento estacionamiento : this.getEstacionados()) {
	        if (estacionamiento.getPatente().equals(patente)) {
	            return estacionamiento;
	        }
		}
		return null;
	}


	public boolean seEncuentraEnZonaDeEstacionamiento() {
		return true;
	}
}
