import java.util.Timer;

public class EstacionamientoVirtual extends Estacionamiento {
    private int phone;
    private final double valorPorHora = 40;
    Timer timer = new Timer();

    public EstacionamientoVirtual(String patente, int phone, ZonaDeEstacionamiento zona){
        super(patente, zona);
        this.phone = phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getPhone() {
        return phone;
    }

    public double getValorPorHora() {
        return valorPorHora;
    }

    public void cobrarHoraDeEstacionamiento(){
        if(super.getSem().getRegistroSaldo().get(phone) > valorPorHora){
            getSem().generarPagoVirtual(phone, valorPorHora);
        }else{
            super.anularValidez();
        }
    }

    public void inicioDeEstacionamiento(){
        super.getSem().iniciarEstacionamiento(super.getPatente(),super.getZona());
    }

    @Override
    public boolean isValidezEstacionamiento(){
        return super.getSem().getRegistroSaldo().get(phone) > valorPorHora;
    }

    public void registrarHoraDeEstacionamiento(){
        /**
         Cada hora que pasa desde que se registra el estacionamiento se descontara el monto de
         la aplicacion asociada al numero telefonico.
         */
        timer.cancel();
    }
}
