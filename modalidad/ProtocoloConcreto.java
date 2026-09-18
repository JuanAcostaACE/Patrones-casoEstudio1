package hemocentro.modalidad;

public class ProtocoloConcreto implements ProtocoloConservacion {

    private final String descripcionTemperatura;
    private final int vigenciaDias;

    public ProtocoloConcreto(String descripcionTemperatura, int vigenciaDias) {
        this.descripcionTemperatura = descripcionTemperatura;
        this.vigenciaDias = vigenciaDias;
    }

    @Override
    public String getDescripcionTemperatura() {
        return descripcionTemperatura;
    }

    @Override
    public int getVigenciaDias() {
        return vigenciaDias;
    }
}
