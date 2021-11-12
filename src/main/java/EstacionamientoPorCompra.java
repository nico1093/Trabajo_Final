import java.util.Date;

public class EstacionamientoPorCompra extends Estacionamiento {
    private int horasFijas;
    private Date fin;

    public EstacionamientoPorCompra(String patente, int horasFijas){
        super(patente);
        this.horasFijas = horasFijas;
        fin = new Date(super.getInicio().getHours() + horasFijas);
    }
}
