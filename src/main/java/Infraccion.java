import java.util.Date;

public class Infraccion {
    private String patente;
    private Date fechaOrigen;
    private double monto;

    public Infraccion(String patente, double monto){
        this.monto = monto;
        this.patente = patente;
        this.fechaOrigen = new Date();
    }
}
