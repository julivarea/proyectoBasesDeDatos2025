package servicios;

import java.sql.*;
import java.util.Scanner;

/**
 * Esta clase se encarga de establecer la conexión con la base de datos al iniciar la App, y provee los metodos basicos de gestión.
 */
public class GestorPadrinos {
    Scanner scanner;
    Connection conn;

    public GestorPadrinos() {
        this.scanner = new Scanner(System.in);
        try {
            this.conn = DatabaseConfig.getConnection();
        }
        catch (SQLException e) {
            System.err.println("Error al establecer la conexión con la base de datos: " + e.getMessage());
            this.conn = null;
        }
    }
    
    /* ============================= FUNCIONES DEL EJERCICIO 5 ============================= */

    public void insertarPadrino() {
        System.out.println("+-+-+-+ INSERTAR PADRINO +-+-+-+");
        try {

        } catch (SQLException e) {
            System.err.println("Error al insertar padrino: " + e.getMessage());
        }
        
    }

    public void eliminarDonante() {
        System.out.println("+-+-+-+ ELIMINAR DONANTE +-+-+-+");
        try {

        } catch (SQLException e) {
            System.err.println("Error al eliminar donante: " + e.getMessage());
        }
    }

    public void listarDonantesYAportes() {
        System.out.println("+-+-+-+ LISTAR DONANTES Y APORTES +-+-+-+");
        try {

        } catch (SQLException e) {
            System.err.println("Error al mostrar los donantes y aportes: " + e.getMessage());
        }
    }

    /* ============================= CONSULTAS DEL EJERCICIO 6 ============================= */
    public void mostrarAportesMensualesPorPrograma() {

    }

    public void mostrarDonantesConMasDeDosProgramas() {

    }

    public void mostrarDonantesAportesMensualesConMediosPago() {

    }
}
