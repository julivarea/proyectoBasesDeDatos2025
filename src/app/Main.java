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
            System.out.println("Seleccione una opcion: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Entrada invalida. Ingrese un numero del 0 al 8: ");
                scanner.next();
            }
            
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
                case 7:
                    gestor.mostrarTodosPadrinos();
                    break;
                case 8:
                    gestor.mostrarAuditoriaEliminacionDonantes();
                    break;
                default:
                    System.out.println("Opcion no valida. Ingrese un numero del 0 al 8: ");
                    break;
            }

        } while (opcion != 0);

        gestor.cerrarScanner();
        scanner.close();
    }

    public static void mostrarMenu() {
        System.out.println("\n");
        System.out.println("============ SISTEMA DE GESTION DE PADRINOS - CIUDAD DE LOS NIÑOS ============");
        System.out.println("1- Insertar padrino");
        System.out.println("2- Eliminar donante");
        System.out.println("3- Listar donantes y aportes");
        System.out.println("4- Mostrar los aportes mensuales para cada programa");
        System.out.println("5- Mostrar los donantes que aportan a mas de dos programas");
        System.out.println("6- Mostrar los donantes con aportes mensuales y los medios de pago utilizados");
        System.out.println("7- Mostrar los padrinos");
        System.out.println("8- Ver registro de auditoria de eliminacion de donantes");
        System.out.println("0- Salir");
        System.out.println("=============================================================================");
    }
}
