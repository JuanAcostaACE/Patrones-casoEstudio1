package hemocentro.modalidad;

public interface Etiqueta {
    String getPrefijo();
    String generarCodigo(String codigoSede, int anio, int consecutivo);
}
