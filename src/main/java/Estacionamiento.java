import java.util.Date;

public abstract class Estacionamiento {
    private String patente;
    private SEM sem = SEM.getInstance();
    private Date inicio;
    private boolean validezEstacionamiento = true;

    public Estacionamiento(String patente){
        this.patente = patente;
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

    public boolean isValidezEstacionamiento() {
        return validezEstacionamiento;
    }

    public void anularValidez(){
        validezEstacionamiento = false;
    }

}
