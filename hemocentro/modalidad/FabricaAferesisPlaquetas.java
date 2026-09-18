package hemocentro.modalidad;

public class FabricaAferesisPlaquetas extends FabricaModalidad {

    @Override
    public Bolsa crearBolsa() {
        return new BolsaConcreta("Apheresis kit PLT", "ACD-A", 300);
    }

    @Override
    public Etiqueta crearEtiqueta() {
        return new EtiquetaConcreta("E30");
    }

    @Override
    public ProtocoloConservacion crearProtocoloConservacion() {
        return new ProtocoloConcreto("20 to 24 \u00B0C with agitation", 5);
    }
}
