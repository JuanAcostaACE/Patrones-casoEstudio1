package hemocentro.colecta;

import hemocentro.modalidad.Bolsa;
import hemocentro.modalidad.Etiqueta;
import hemocentro.modalidad.FabricaModalidad;
import hemocentro.modalidad.Modalidad;
import hemocentro.modalidad.ProtocoloConservacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RegistroDeColecta {

    private final int consecutivo;
    private final String documentoDonante;
    private final String codigoSede;
    private final LocalDate fechaColecta;
    private final Modalidad modalidad;
    private final int volumenRealMl;
    private final int tiempoPuncionMin;
    private final String flebotomista;
    private final String loteInsumos;

    private final Bolsa bolsa;
    private final Etiqueta etiqueta;
    private final ProtocoloConservacion protocolo;

    private final String campania;
    private final String observaciones;
    private final List<String> eventosAdversos;
    private final boolean donantePrimeraVez;

    private final String codigoUnidad;
    private final boolean apta;
    private final String causalNoApta;

    private RegistroDeColecta(Builder b) {
        this.consecutivo = b.consecutivo;
        this.documentoDonante = b.documentoDonante;
        this.codigoSede = b.codigoSede;
        this.fechaColecta = b.fechaColecta;
        this.modalidad = b.modalidad;
        this.volumenRealMl = b.volumenRealMl;
        this.tiempoPuncionMin = b.tiempoPuncionMin;
        this.flebotomista = b.flebotomista;
        this.loteInsumos = b.loteInsumos;
        this.campania = b.campania;
        this.observaciones = b.observaciones;
        this.eventosAdversos = b.eventosAdversos == null
                ? Collections.emptyList()
                : Collections.unmodifiableList(new ArrayList<>(b.eventosAdversos));
        this.donantePrimeraVez = b.donantePrimeraVez;

        FabricaModalidad fabrica = FabricaModalidad.obtenerFabrica(modalidad);
        this.bolsa = fabrica.crearBolsa();
        this.etiqueta = fabrica.crearEtiqueta();
        this.protocolo = fabrica.crearProtocoloConservacion();

        this.codigoUnidad = etiqueta.generarCodigo(codigoSede, fechaColecta.getYear(), consecutivo);

        int nominal = bolsa.getVolumenNominal();
        double minimo = nominal * 0.90;
        double maximo = nominal * 1.10;
        if (volumenRealMl < minimo || volumenRealMl > maximo) {
            this.apta = false;
            this.causalNoApta = "VOLUMEN_FUERA_DE_RANGO";
        } else {
            this.apta = true;
            this.causalNoApta = null;
        }
    }

    public int getConsecutivo() { return consecutivo; }
    public String getDocumentoDonante() { return documentoDonante; }
    public String getCodigoSede() { return codigoSede; }
    public LocalDate getFechaColecta() { return fechaColecta; }
    public Modalidad getModalidad() { return modalidad; }
    public int getVolumenRealMl() { return volumenRealMl; }
    public int getTiempoPuncionMin() { return tiempoPuncionMin; }
    public String getFlebotomista() { return flebotomista; }
    public String getLoteInsumos() { return loteInsumos; }
    public Bolsa getBolsa() { return bolsa; }
    public Etiqueta getEtiqueta() { return etiqueta; }
    public ProtocoloConservacion getProtocolo() { return protocolo; }
    public String getCampania() { return campania; }
    public String getObservaciones() { return observaciones; }
    public List<String> getEventosAdversos() { return eventosAdversos; }
    public boolean isDonantePrimeraVez() { return donantePrimeraVez; }
    public String getCodigoUnidad() { return codigoUnidad; }
    public boolean isApta() { return apta; }
    public String getCausalNoApta() { return causalNoApta; }

    public LocalDate getFechaVencimiento() {
        return fechaColecta.plusDays(protocolo.getVigenciaDias());
    }

    // ---- Builder ----

    public static class Builder {

        private int consecutivo;
        private String documentoDonante;
        private String codigoSede;
        private LocalDate fechaColecta;
        private Modalidad modalidad;
        private int volumenRealMl;
        private int tiempoPuncionMin;
        private String flebotomista;
        private String loteInsumos;

        private String campania;
        private String observaciones;
        private List<String> eventosAdversos;
        private boolean donantePrimeraVez;

        private boolean consecutivoSet;
        private boolean volumenSet;
        private boolean tiempoSet;

        public Builder consecutivo(int val) {
            this.consecutivo = val;
            this.consecutivoSet = true;
            return this;
        }

        public Builder documentoDonante(String val) {
            this.documentoDonante = val;
            return this;
        }

        public Builder codigoSede(String val) {
            this.codigoSede = val;
            return this;
        }

        public Builder fechaColecta(LocalDate val) {
            this.fechaColecta = val;
            return this;
        }

        public Builder modalidad(Modalidad val) {
            this.modalidad = val;
            return this;
        }

        public Builder volumenRealMl(int val) {
            this.volumenRealMl = val;
            this.volumenSet = true;
            return this;
        }

        public Builder tiempoPuncionMin(int val) {
            this.tiempoPuncionMin = val;
            this.tiempoSet = true;
            return this;
        }

        public Builder flebotomista(String val) {
            this.flebotomista = val;
            return this;
        }

        public Builder loteInsumos(String val) {
            this.loteInsumos = val;
            return this;
        }

        public Builder campania(String val) {
            this.campania = val;
            return this;
        }

        public Builder observaciones(String val) {
            this.observaciones = val;
            return this;
        }

        public Builder eventosAdversos(List<String> val) {
            this.eventosAdversos = val;
            return this;
        }

        public Builder donantePrimeraVez(boolean val) {
            this.donantePrimeraVez = val;
            return this;
        }

        public RegistroDeColecta build() {
            if (!consecutivoSet)
                throw new IllegalStateException("missing 'consecutivo'");
            if (documentoDonante == null || documentoDonante.isBlank())
                throw new IllegalStateException("missing 'documentoDonante'");
            if (codigoSede == null || codigoSede.isBlank())
                throw new IllegalStateException("missing 'codigoSede'");
            if (fechaColecta == null)
                throw new IllegalStateException("missing 'fechaColecta'");
            if (modalidad == null)
                throw new IllegalStateException("missing 'modalidad'");
            if (!volumenSet)
                throw new IllegalStateException("missing 'volumenRealMl'");
            if (!tiempoSet)
                throw new IllegalStateException("missing 'tiempoPuncionMin'");
            if (flebotomista == null || flebotomista.isBlank())
                throw new IllegalStateException("missing 'flebotomista'");
            if (loteInsumos == null || loteInsumos.isBlank())
                throw new IllegalStateException("missing 'loteInsumos'");

            if (tiempoPuncionMin > 15 && (observaciones == null || observaciones.isBlank())) {
                throw new IllegalStateException(
                        "puncture time exceeds 15 min without recorded observation");
            }

            if (eventosAdversos != null && !eventosAdversos.isEmpty()
                    && (flebotomista == null || flebotomista.isBlank())) {
                throw new IllegalStateException(
                        "adverse events present but no responsible phlebotomist specified");
            }

            return new RegistroDeColecta(this);
        }
    }
}
