package hemocentro.modalidad;

public class FabricaSangreTotal extends FabricaModalidad {

    @Override
    public Bolsa crearBolsa() {
        return new BolsaConcreta("Quadruple bag with filter", "CPD-A1", 450);
    }

    @Override
    public Etiqueta crearEtiqueta() {
        return new EtiquetaConcreta("E00");
    }

    @Override
    public ProtocoloConservacion crearProtocoloConservacion() {
        return new ProtocoloConcreto("2 to 6 \u00B0C", 35);
    }
}
