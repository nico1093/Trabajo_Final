public class EstacionamientoVirtual extends Estacionamiento {
    private int phone;
    private final double valorPorHora = 40;


    public EstacionamientoVirtual(String patente, int phone){
        super(patente);
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
}
