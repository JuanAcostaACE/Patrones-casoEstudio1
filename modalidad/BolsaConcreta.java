package hemocentro.modalidad;

public class BolsaConcreta implements Bolsa {

    private final String descripcion;
    private final String anticoagulante;
    private final int volumenNominal;

    public BolsaConcreta(String descripcion, String anticoagulante, int volumenNominal) {
        this.descripcion = descripcion;
        this.anticoagulante = anticoagulante;
        this.volumenNominal = volumenNominal;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String getAnticoagulante() {
        return anticoagulante;
    }

    @Override
    public int getVolumenNominal() {
        return volumenNominal;
    }
}
