package hemocentro.app;

import hemocentro.colecta.RegistroDeColecta;
import hemocentro.jornada.Horario;
import hemocentro.jornada.PlantillaJornada;
import hemocentro.modalidad.Modalidad;
import hemocentro.servicio.ServicioInventario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== REGIONAL BLOOD CENTER - NORTHEAST ===");
        System.out.println();

        // ---------- PROTOTYPE: mobile drive templates ----------

        Horario horarioBase = new Horario(LocalTime.of(7, 0), LocalTime.of(13, 0));
        List<String> insumosBase = new ArrayList<>(List.of(
                "Quadruple bags", "16G needles", "Sample tubes", "Antiseptic alcohol"));

        PlantillaJornada plantillaBase = new PlantillaJornada(
                "Standard university drive", "UNI", "---",
                80, Modalidad.SANGRE_TOTAL, horarioBase, insumosBase);

        System.out.println("Base template: " + plantillaBase.getNombre()
                + " | supplies: " + plantillaBase.getInsumos().size());

        PlantillaJornada jornadaUIS = plantillaBase.clone();
        jornadaUIS.setNombre("UIS - Bucaramanga");
        jornadaUIS.setSede("BUC");
        jornadaUIS.setMunicipio("Bucaramanga");

        PlantillaJornada jornadaUFPS = plantillaBase.clone();
        jornadaUFPS.setNombre("UFPS - Cucuta");
        jornadaUFPS.setSede("CUC");
        jornadaUFPS.setMunicipio("Cucuta");
        jornadaUFPS.setMetaUnidades(120);
        jornadaUFPS.agregarInsumo("Thermal tent");

        System.out.println("Cloned drive 1: " + jornadaUIS.getNombre()
                + " | goal: " + jornadaUIS.getMetaUnidades()
                + " | supplies: " + jornadaUIS.getInsumos().size());
        System.out.println("Cloned drive 2: " + jornadaUFPS.getNombre()
                + " | goal: " + jornadaUFPS.getMetaUnidades()
                + " | supplies: " + jornadaUFPS.getInsumos().size());

        System.out.println("Base template verification -> supplies: "
                + plantillaBase.getInsumos().size() + " (NOT modified)");

        // ---------- BUILDER + ABSTRACT FACTORY: collection records ----------

        List<RegistroDeColecta> registros = new ArrayList<>();

        // 1. Whole blood, BUC, fit
        registros.add(new RegistroDeColecta.Builder()
                .consecutivo(1)
                .documentoDonante("1098765432")
                .codigoSede("BUC")
                .fechaColecta(LocalDate.of(2026, 3, 20))
                .modalidad(Modalidad.SANGRE_TOTAL)
                .volumenRealMl(455)
                .tiempoPuncionMin(8)
                .flebotomista("Carlos Ruiz")
                .loteInsumos("LOTE-2026-001")
                .build());

        // 2. Platelet apheresis, BUC, fit
        registros.add(new RegistroDeColecta.Builder()
                .consecutivo(2)
                .documentoDonante("37845612")
                .codigoSede("BUC")
                .fechaColecta(LocalDate.of(2026, 4, 13))
                .modalidad(Modalidad.AFERESIS_PLAQUETAS)
                .volumenRealMl(298)
                .tiempoPuncionMin(55)
                .flebotomista("Maria Lopez")
                .loteInsumos("LOTE-2026-002")
                .observaciones("Normal apheresis procedure")
                .build());

        // 3. Whole blood, CUC, UNFIT (390 < 405 = 450*0.9)
        registros.add(new RegistroDeColecta.Builder()
                .consecutivo(3)
                .documentoDonante("91234567")
                .codigoSede("CUC")
                .fechaColecta(LocalDate.of(2026, 3, 22))
                .modalidad(Modalidad.SANGRE_TOTAL)
                .volumenRealMl(390)
                .tiempoPuncionMin(10)
                .flebotomista("Jorge Pena")
                .loteInsumos("LOTE-2026-003")
                .build());

        // 4. Plasma apheresis, CUC, fit
        registros.add(new RegistroDeColecta.Builder()
                .consecutivo(4)
                .documentoDonante("28456789")
                .codigoSede("CUC")
                .fechaColecta(LocalDate.of(2026, 4, 10))
                .modalidad(Modalidad.AFERESIS_PLASMA)
                .volumenRealMl(612)
                .tiempoPuncionMin(45)
                .flebotomista("Ana Torres")
                .loteInsumos("LOTE-2026-004")
                .observaciones("Plasma apheresis without issues")
                .build());

        // 5. Platelet apheresis, CUC, fit
        registros.add(new RegistroDeColecta.Builder()
                .consecutivo(5)
                .documentoDonante("63012345")
                .codigoSede("CUC")
                .fechaColecta(LocalDate.of(2026, 4, 18))
                .modalidad(Modalidad.AFERESIS_PLAQUETAS)
                .volumenRealMl(303)
                .tiempoPuncionMin(65)
                .flebotomista("Luis Gomez")
                .loteInsumos("LOTE-2026-005")
                .observaciones("Procedure completed without issues")
                .build());

        // 6. Whole blood, BUC, fit, with adverse events
        registros.add(new RegistroDeColecta.Builder()
                .consecutivo(6)
                .documentoDonante("55678901")
                .codigoSede("BUC")
                .fechaColecta(LocalDate.of(2026, 3, 25))
                .modalidad(Modalidad.SANGRE_TOTAL)
                .volumenRealMl(450)
                .tiempoPuncionMin(12)
                .flebotomista("Pedro Diaz")
                .loteInsumos("LOTE-2026-006")
                .eventosAdversos(List.of("Mild hematoma at puncture site"))
                .donantePrimeraVez(true)
                .build());

        // ---------- incomplete record attempt ----------

        try {
            new RegistroDeColecta.Builder()
                    .consecutivo(99)
                    .documentoDonante("00000000")
                    .codigoSede("BUC")
                    .fechaColecta(LocalDate.of(2026, 4, 1))
                    .modalidad(Modalidad.SANGRE_TOTAL)
                    .volumenRealMl(450)
                    .tiempoPuncionMin(7)
                    .flebotomista("Test")
                    // missing loteInsumos
                    .build();
        } catch (IllegalStateException e) {
            System.out.println();
            System.out.println("[CONTROLLED ERROR] Cannot build record: " + e.getMessage());
        }

        // ---------- REPORT ----------

        ServicioInventario servicio = new ServicioInventario();
        servicio.imprimirUnidades(registros);
        servicio.imprimirConsolidado(registros);
        servicio.imprimirAlertaFEFO(registros, LocalDate.of(2026, 4, 20));
    }
}
