package hemocentro.modalidad;

public class EtiquetaConcreta implements Etiqueta {

    private final String prefijo;

    public EtiquetaConcreta(String prefijo) {
        this.prefijo = prefijo;
    }

    @Override
    public String getPrefijo() {
        return prefijo;
    }

    @Override
    public String generarCodigo(String codigoSede, int anio, int consecutivo) {
        return String.format("%s-%s-%02d-%05d", prefijo, codigoSede, anio % 100, consecutivo);
    }
}
