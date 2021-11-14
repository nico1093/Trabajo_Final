package main.java;

public class Inspector {
	private String nombreInspector;
    private final SEM sem = SEM.getInstance();
    private ZonaDeEstacionamiento zonaEncargada;

    public Inspector(String nombre,ZonaDeEstacionamiento zonaEncargada){
        this.zonaEncargada = zonaEncargada;
        this.setNombreInspector(nombre);
    }

    

	public void inspeccionarEstacionamiento(String patente){
        /**
         * Este mensaje verificara si el estacionamiento se encuentra en validez. En el caso que no se encuentre
         * valido este generara una infraccion.
         */
        if(!sem.esValidoElEstacionamiento(patente)){
            sem.generarInfraccion(patente, this.zonaEncargada);
        }
    }

	public String getNombreInspector() {
		return nombreInspector;
	}

	public void setNombreInspector(String nombreInspector) {
		this.nombreInspector = nombreInspector;
	}
	
	public ZonaDeEstacionamiento getZonaEncargada() {
		return zonaEncargada;
	}

	public void setZonaEncargada(ZonaDeEstacionamiento zonaEncargada) {
		this.zonaEncargada = zonaEncargada;
	}

	 public SEM getSem() {
	        return sem;
	    }
}
