package hemocentro.jornada;

import java.time.LocalTime;

public class Horario {

    private LocalTime horaInicio;
    private LocalTime horaFin;

    public Horario(LocalTime horaInicio, LocalTime horaFin) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Horario(Horario otro) {
        this.horaInicio = otro.horaInicio;
        this.horaFin = otro.horaFin;
    }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    @Override
    public String toString() {
        return horaInicio + " - " + horaFin;
    }
}
