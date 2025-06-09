package app;

import java.util.*;
import servicios.GestorPadrinos;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hola mundo");
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
                    //gestor.algo();
                    break;
                case 2:
                    //gestor.algo();
                    break;
                case 3:
                    //gestor.algo();
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
        System.out.println("1-");
        System.out.println("2-");
        System.out.println("3-");
        // ETC...
    }
}
