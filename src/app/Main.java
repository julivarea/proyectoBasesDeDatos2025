package app;

import java.util.*;
import servicios.GestorPadrinos;

public class Main {
    public static void main(String[] args) {
        GestorPadrinos gestor = new GestorPadrinos();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            System.out.println("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                case 1:
                    gestor.insertarPadrino();
                    break;
                case 2:
                    gestor.eliminarDonante();
                    break;
                case 3:
                    gestor.listarDonantesYAportes();
                    break;
                case 4:
                    gestor.mostrarTotalAportesPorProgama();
                    break;
                case 5:
                    gestor.mostrarDonantesConMasDeDosProgramas();
                    break;
                case 6:
                    gestor.mostrarDonantesAportesMensualesConMediosPago();
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }

        } while (opcion != 0);

        scanner.close();
    }

    public static void mostrarMenu() {
        System.out.println("\n");
        System.out.println("============ SISTEMA DE GESTION DE PADRINOS - CIUDAD DE LOS NIÑOS ============");
        System.out.println("1- Insertar padrino");
        System.out.println("2- Eliminar donante");
        System.out.println("3- Listar donantes y aportes");
        System.out.println("4- Mostrar los aportes mensuales para cada programa");
        System.out.println("5- Mostrar los donantes que aportan a más de dos programas");
        System.out.println("6- Mostrar los donantes con aportes mensuales y los medios de pago utilizados");
        System.out.println("0- Salir");
        System.out.println("=============================================================================");

        // ETC...
    }
}
