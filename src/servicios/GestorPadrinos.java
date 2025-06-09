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
    // ...existing code...

    public void mostrarTotalAportesPorProgama() {
        System.out.println("+-+-+-+ TOTAL APORTES POR PROGRAMA +-+-+-+");
        try {
            String sql = "SELECT nombrePrograma, SUM(monto) AS totalAportado " +
                         "FROM Aporta GROUP BY nombrePrograma";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.printf("%-20s %-15s%n", "Programa", "Total Aportado");
            while (rs.next()) {
                System.out.printf("%-20s %-15.2f%n",
                    rs.getString("nombrePrograma"),
                    rs.getDouble("totalAportado")
                );
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.err.println("Error al mostrar el total de aportes por programa: " + e.getMessage());
        }
    }

    public void mostrarDonantesConMasDeDosProgramas() {
        System.out.println("+-+-+-+ DONANTES CON MÁS DE DOS PROGRAMAS +-+-+-+");
        try {
            String sql = "SELECT d.dni, d.cuit, d.ocupacion, COUNT(a.nombrePrograma) AS cantidadProgramas " +
                         "FROM Donante d JOIN Aporta a ON d.dni = a.dni " +
                         "GROUP BY d.dni, d.cuit, d.ocupacion " +
                         "HAVING COUNT(a.nombrePrograma) > 2";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.printf("%-10s %-15s %-15s %-20s%n", "DNI", "CUIT", "Ocupación", "Cantidad Programas");
            while (rs.next()) {
                System.out.printf("%-10s %-15s %-15s %-20d%n",
                    rs.getString("dni"),
                    rs.getString("cuit"),
                    rs.getString("ocupacion"),
                    rs.getInt("cantidadProgramas")
                );
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.err.println("Error al mostrar donantes con más de dos programas: " + e.getMessage());
        }
    }

    public void mostrarDonantesAportesMensualesConMediosPago() {
        System.out.println("+-+-+-+ DONANTES CON APORTES MENSUALES Y MEDIOS DE PAGO +-+-+-+");
        try {
            String sql = "SELECT d.dni, d.cuit, d.ocupacion, a.nombrePrograma, a.monto, m.nombreTitular " +
                         "FROM Donante d " +
                         "JOIN Aporta a ON d.dni = a.dni " +
                         "LEFT JOIN MedioDePago m ON a.idMP = m.id " +
                         "WHERE a.frecuencia = 'Mensual'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.printf("%-10s %-15s %-15s %-20s %-10s %-20s%n", "DNI", "CUIT", "Ocupación", "Programa", "Monto", "Medio de Pago");
            while (rs.next()) {
                System.out.printf("%-10s %-15s %-15s %-20s %-10.2f %-20s%n",
                    rs.getString("dni"),
                    rs.getString("cuit"),
                    rs.getString("ocupacion"),
                    rs.getString("nombrePrograma"),
                    rs.getDouble("monto"),
                    rs.getString("nombreTitular")
                );
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.err.println("Error al mostrar donantes con aportes mensuales y medios de pago: " + e.getMessage());
        }
    }

}
