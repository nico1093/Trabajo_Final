package main.java.SEM.Estacionamiento;

import main.java.SEM.Entidades.Comercio;
import main.java.SEM.Entidades.Inspector;
import main.java.SEM.SEM;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	public Inspector getEncargado() {
		return encargado;
	}





	public List<Comercio> getComercios() {
		return comercios;
	}







	public List<Estacionamiento> getEstacionados() {
		return estacionados;
	}


	public void asignarInspector(Inspector inspector) {
		this.encargado = inspector;
	}





	public boolean estacionamientoEsVigente(String patente) {
		
		return this.getEstacionados().stream().anyMatch(e -> e.getPatente().equals(patente) && e.esVigente());
	}
	

	public Estacionamiento estacionamientoDe(String patente) {
		
		Estacionamiento estacionamiento = this.getEstacionados().stream().filter(s -> s.getPatente().equals(patente)).reduce((first, second) -> second).get();
		return estacionamiento;
		
		
	}




	public boolean estaDentroDeLaZona() {
		// TODO Auto-generated method stub
		return true;
	}


	
}
