package hemocentro.jornada;

import hemocentro.modalidad.Modalidad;
import java.util.ArrayList;
import java.util.List;

public class PlantillaJornada implements Cloneable {

    private String nombre;
    private String sede;
    private String municipio;
    private int metaUnidades;
    private Modalidad modalidadPorDefecto;
    private Horario horario;
    private List<String> insumos;

    public PlantillaJornada(String nombre, String sede, String municipio,
                            int metaUnidades, Modalidad modalidadPorDefecto,
                            Horario horario, List<String> insumos) {
        this.nombre = nombre;
        this.sede = sede;
        this.municipio = municipio;
        this.metaUnidades = metaUnidades;
        this.modalidadPorDefecto = modalidadPorDefecto;
        this.horario = horario;
        this.insumos = new ArrayList<>(insumos);
    }

    @Override
    public PlantillaJornada clone() {
        try {
            PlantillaJornada copia = (PlantillaJornada) super.clone();
            copia.horario = new Horario(this.horario);
            copia.insumos = new ArrayList<>(this.insumos);
            return copia;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getSede() { return sede; }
    public void setSede(String sede) { this.sede = sede; }
    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }
    public int getMetaUnidades() { return metaUnidades; }
    public void setMetaUnidades(int metaUnidades) { this.metaUnidades = metaUnidades; }
    public Modalidad getModalidadPorDefecto() { return modalidadPorDefecto; }
    public void setModalidadPorDefecto(Modalidad m) { this.modalidadPorDefecto = m; }
    public Horario getHorario() { return horario; }
    public void setHorario(Horario horario) { this.horario = horario; }
    public List<String> getInsumos() { return insumos; }
    public void agregarInsumo(String insumo) { this.insumos.add(insumo); }
}
