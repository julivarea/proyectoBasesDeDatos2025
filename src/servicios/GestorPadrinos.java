package servicios;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
            String dni;
            do {
                System.out.println("Ingrese el dni del padrino (8 digitos)");
                dni = scanner.nextLine();
                if (dni.length() != 8 || !dni.matches("\\d{8}")) { // Revisamos que tenga 8 caracteres que sean solo digitos (usando regex)
                    System.out.println("El DNI debe tener 8 dígitos. Intente nuevamente.");
                    dni = null;
                    continue;
                }

                // Validar que no haya nadie con ese dni
                String checkSql = "SELECT COUNT(*) FROM Padrino WHERE dni = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setString(1, dni);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("El DNI ingresado ya existe. Intente con otro.");
                    dni = null;
                }
                rs.close();
                checkStmt.close();
            } while (dni == null);

            /*
            String cuit;
            do {
                System.out.println("Ingrese el cuit del padrino");
                cuit = scanner.nextLine();
                if (cuit.length() != 11) {
                    System.out.println("El CUIT debe tener 11 dígitos (solo numeros). Intente nuevamente.");
                }
            } while (cuit.length() != 11);
            */

            System.out.println("Ingrese el nombre del padrino");
            String nombre = scanner.nextLine();

            System.out.println("Ingrese el apellido del padrino");
            String apellido = scanner.nextLine();

            System.out.println("Ingrese la direccion del padrino");
            String direccion = scanner.nextLine();

            System.out.println("Ingrese el codigo postal del padrino");
            String codigoPostal = scanner.nextLine();

            System.out.println("Ingrese el email del padrino");
            String email = scanner.nextLine();

            System.out.println("Ingrese el facebook del padrino");
            String facebook = scanner.nextLine();

            System.out.println("Ingrese el telefono fijo del padrino");
            String telFijo = scanner.nextLine();

            System.out.println("Ingrese el telefono celular del padrino");
            String telCelular = scanner.nextLine();

            String fechaNacimientoStr = null;
            LocalDate fechaNacimiento = null;
            while (fechaNacimiento == null) {
                System.out.println("Ingrese la fecha de nacimiento del padrino (AAAA-MM-DD)");
                fechaNacimientoStr = scanner.nextLine();
                try {
                    fechaNacimiento = LocalDate.parse(fechaNacimientoStr, DateTimeFormatter.ISO_LOCAL_DATE);
                } catch (DateTimeParseException e) {
                    System.out.println("Fecha inválida. Por favor, ingrese la fecha en el formato correcto (AAAA-MM-DD).");
                }
            }

            /*
            System.out.println("Ingrese la ocupacion del padrino");
            String ocupacion = scanner.nextLine();
            */

            String sql = "INSERT INTO Padrino (dni, nombre, apellido, direccion, codigoPostal, email, facebook, telFijo, telCelular, fechaNacimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
           
            // Insertar el padrino en la base de datos
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dni);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apellido);
            pstmt.setString(4, direccion);
            pstmt.setString(5, codigoPostal);
            pstmt.setString(6, email);
            pstmt.setString(7, facebook);
            pstmt.setString(8, telFijo);
            pstmt.setString(9, telCelular);
            pstmt.setDate(10, java.sql.Date.valueOf(fechaNacimiento));
            
            pstmt.executeUpdate();
            System.out.println("Padrino insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar padrino: " + e.getMessage());
        }
    }

    public void eliminarDonante() {
        System.out.println("+-+-+-+ ELIMINAR DONANTE +-+-+-+");
        try {
            String dni;
            do {
                System.out.println("Ingrese el dni del donante a eliminar (8 digitos)");
                dni = scanner.nextLine();
                if (dni.length() != 8 || !dni.matches("\\d{8}")) { // Revisamos que tenga 8 caracteres que sean solo digitos (usando regex)
                    System.out.println("El DNI debe tener 8 dígitos. Intente nuevamente.");
                    dni = null;
                    continue;
                }

                // Validar que haya alguien con ese dni
                String checkSql = "SELECT * FROM Donante WHERE dni = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setString(1, dni);
                ResultSet rs = checkStmt.executeQuery();

                if (!rs.next()) {
                    System.out.println("No hay un donante registrado con ese DNI.");
                    rs.close();
                    checkStmt.close();
                    return; // salimos sin borrar
                }
                rs.close();
                checkStmt.close();

                // Borramos el donante
                String deleteSQL = "DELETE FROM Donante WHERE dni= ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL);
                deleteStmt.setString(1, dni);
                deleteStmt.executeUpdate();
                deleteStmt.close();

            } while (dni == null);
        } catch (SQLException e) {
            System.err.println("Error al eliminar donante: " + e.getMessage());
        }
    }

    public void listarDonantesYAportes() {
        System.out.println("+-+-+-+ LISTAR DONANTES Y APORTES +-+-+-+");
        try {
            String sql = """
                            SELECT 
                                d.dni, p.nombre, p.apellido, 
                                a.nombrePrograma, a.monto, a.frecuencia 
                                FROM Donante d 
                                JOIN Aporta a ON d.dni = a.dni 
                                JOIN Padrino p ON d.dni = p.dni
                                ORDER BY p.apellido, p.nombre
            """;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            System.out.printf("%-12s %-15s %-15s %10s %-10s%n", "DNI", "Nombre", "Apellido", "Monto", "Frecuencia");
            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                double monto = rs.getDouble("monto");
                String frecuencia = rs.getString("frecuencia");

                System.out.printf("%-12s %-15s %-15s %10.2f %-10s%n", dni, nombre, apellido, monto, frecuencia);
            }
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
