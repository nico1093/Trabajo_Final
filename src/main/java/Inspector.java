public class Inspector {
    private final SEM sem = SEM.getInstance();
    private ZonaDeEstacionamiento zonaEncargada;

    public Inspector(ZonaDeEstacionamiento zonaEncargada){
        this.zonaEncargada = zonaEncargada;
    }

    public void inspeccionarEstacionamiento(String patente){
        /**
         * Este mensaje verificara si el estacionamiento se encuentra en validez. En el caso que no se encuentre
         * valido este generara una infraccion.
         */
        if(!sem.esValidoElEstacionamiento(patente)){
            sem.generarInfraccion(patente);
        }
    }

}
