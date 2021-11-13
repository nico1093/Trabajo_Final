import java.util.Calendar;
import java.util.Date;

public class EstacionamientoPorCompra extends Estacionamiento {
    private Date finEstacionamiento;

    public EstacionamientoPorCompra(String patente, int horasFijas, ZonaDeEstacionamiento zona){
        super(patente, zona);
        //Calendario para poder modificar la hora actual.
        Calendar finCal = Calendar.getInstance();
        finCal.setTime(new Date());
        finCal.set(Calendar.HOUR, finCal.get(Calendar.HOUR) + horasFijas);
        finEstacionamiento = finCal.getTime();
    }

    public void inicioDeEstacionamiento(){
        super.getSem().iniciarEstacionamiento(super.getPatente(),super.getZona());
    }

    @Override
    public boolean isValidezEstacionamiento(){
        Date fechaActual = new Date();
        return fechaActual.before(finEstacionamiento);
    }
}
