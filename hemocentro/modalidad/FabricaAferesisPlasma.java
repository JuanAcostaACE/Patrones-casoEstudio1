package hemocentro.modalidad;

public class FabricaAferesisPlasma extends FabricaModalidad {

    @Override
    public Bolsa crearBolsa() {
        return new BolsaConcreta("Apheresis kit PLS", "ACD-A", 600);
    }

    @Override
    public Etiqueta crearEtiqueta() {
        return new EtiquetaConcreta("E70");
    }

    @Override
    public ProtocoloConservacion crearProtocoloConservacion() {
        return new ProtocoloConcreto("-25 \u00B0C or below", 365);
    }
}
