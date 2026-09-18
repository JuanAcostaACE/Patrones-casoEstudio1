package hemocentro.modalidad;

public abstract class FabricaModalidad {

    public abstract Bolsa crearBolsa();
    public abstract Etiqueta crearEtiqueta();
    public abstract ProtocoloConservacion crearProtocoloConservacion();

    public static FabricaModalidad obtenerFabrica(Modalidad modalidad) {
        return switch (modalidad) {
            case SANGRE_TOTAL -> new FabricaSangreTotal();
            case AFERESIS_PLAQUETAS -> new FabricaAferesisPlaquetas();
            case AFERESIS_PLASMA -> new FabricaAferesisPlasma();
        };
    }
}
