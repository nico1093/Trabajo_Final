import java.util.Date;

public abstract class Estacionamiento {
    private String patente;
    private SEM sem = SEM.getInstance();
    private Date inicio;
    private boolean validezEstacionamiento = true;
    private ZonaDeEstacionamiento zona;

    public Estacionamiento(String patente, ZonaDeEstacionamiento zona){
        this.patente = patente;
        inicio = new Date();
        this.zona = zona;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getPatente() {
        return patente;
    }

    public Date getInicio() {
        return inicio;
    }

    public SEM getSem() {
        return sem;
    }

    public ZonaDeEstacionamiento getZona() {
        return zona;
    }

    public abstract boolean isValidezEstacionamiento();

    public void anularValidez(){
        validezEstacionamiento = false;
    }

}
