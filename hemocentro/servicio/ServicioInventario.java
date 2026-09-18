package hemocentro.servicio;

import hemocentro.colecta.RegistroDeColecta;
import hemocentro.modalidad.Modalidad;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ServicioInventario {

    public LocalDate calcularVencimiento(RegistroDeColecta registro) {
        return registro.getFechaColecta().plusDays(
                registro.getProtocolo().getVigenciaDias());
    }

    public void imprimirUnidades(List<RegistroDeColecta> registros) {
        System.out.println();
        System.out.println("--- REGISTERED UNITS ---");
        for (RegistroDeColecta r : registros) {
            if (r.isApta()) {
                System.out.printf("%s | %s | %d mL | FIT | expires %s%n",
                        r.getCodigoUnidad(), r.getModalidad(),
                        r.getVolumenRealMl(), r.getFechaVencimiento());
            } else {
                System.out.printf("%s | %s | %d mL | UNFIT | %s%n",
                        r.getCodigoUnidad(), r.getModalidad(),
                        r.getVolumenRealMl(), r.getCausalNoApta());
            }
        }
    }

    public void imprimirConsolidado(List<RegistroDeColecta> registros) {
        System.out.println();
        System.out.println("--- SUMMARY BY MODALITY ---");
        System.out.printf("%-25s %5s %7s %12s %10s %15s%n",
                "Modality", "Fit", "Unfit", "Usable(mL)", "Yield", "Avg puncture");

        for (Modalidad m : Modalidad.values()) {
            List<RegistroDeColecta> delTipo = new ArrayList<>();
            for (RegistroDeColecta r : registros) {
                if (r.getModalidad() == m) delTipo.add(r);
            }
            if (delTipo.isEmpty()) continue;

            int aptas = 0;
            int volUtil = 0;
            int sumaPuncion = 0;

            for (RegistroDeColecta r : delTipo) {
                sumaPuncion += r.getTiempoPuncionMin();
                if (r.isApta()) {
                    aptas++;
                    volUtil += r.getVolumenRealMl();
                }
            }

            int noAptas = delTipo.size() - aptas;
            double aprovechamiento = (aptas * 100.0) / delTipo.size();
            double promPuncion = (double) sumaPuncion / delTipo.size();

            System.out.printf("%-25s %5d %7d %12d %9.1f %% %12.1f min%n",
                    m, aptas, noAptas, volUtil, aprovechamiento, promPuncion);
        }
    }

    public void imprimirAlertaFEFO(List<RegistroDeColecta> registros, LocalDate fechaCorte) {
        System.out.println();
        System.out.println("--- FEFO ALERT (cutoff " + fechaCorte + ", expiring in <= 7 days) ---");

        List<RegistroDeColecta> alertas = new ArrayList<>();
        for (RegistroDeColecta r : registros) {
            if (!r.isApta()) continue;
            long dias = ChronoUnit.DAYS.between(fechaCorte, r.getFechaVencimiento());
            if (dias <= 7) alertas.add(r);
        }

        alertas.sort(Comparator.comparing(RegistroDeColecta::getFechaVencimiento)
                .thenComparingInt(RegistroDeColecta::getConsecutivo));

        if (alertas.isEmpty()) {
            System.out.println("  No units near expiration.");
            return;
        }

        int i = 1;
        for (RegistroDeColecta r : alertas) {
            LocalDate venc = r.getFechaVencimiento();
            long dias = ChronoUnit.DAYS.between(fechaCorte, venc);
            String estado = dias < 0 ? " (EXPIRED)" : "";
            System.out.printf("%d. %s | %s | expires %s | %d days left%s%n",
                    i++, r.getCodigoUnidad(), r.getModalidad(), venc, dias, estado);
        }
    }
}
