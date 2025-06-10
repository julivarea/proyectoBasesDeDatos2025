package servicios;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Esta clase se encarga de establecer la conexion con la base de datos al iniciar la App, y provee los metodos basicos de gestion.
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
            System.err.println("Error al establecer la conexion con la base de datos: " + e.getMessage());
            this.conn = null;
        }
    }
    
    /* ============================= FUNCIONES DEL EJERCICIO 5 ============================= */

    public void insertarPadrino() {
        System.out.println("\n+-+-+-+-+-+-+-+-+-+ INSERTAR PADRINO +-+-+-+-+-+-+-+-+-+");
        try {
            String dni;
            do {
                dni = solicitarStringConLongitud("Ingrese el dni del padrino (8 digitos)", 8, 8, "El DNI");
                
                // Validar que contenga solo digitos
                if (!dni.matches("\\d{8}")) { 
                    System.out.println("El DNI debe contener solo digitos. Intente nuevamente.");
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

            String nombre = solicitarStringConLongitud("Ingrese el nombre del padrino (maximo 50 caracteres)", 1, 50, "El nombre");
            String apellido = solicitarStringConLongitud("Ingrese el apellido del padrino (maximo 50 caracteres)", 1, 50, "El apellido");
            String direccion = solicitarStringConLongitud("Ingrese la direccion del padrino (maximo 200 caracteres)", 1, 200, "La direccion");
            String codigoPostal = solicitarStringConLongitud("Ingrese el codigo postal (maximo 10 caracteres)", 1, 10, "El codigo postal");
            String email = solicitarStringConLongitud("Ingrese el email del padrino (maximo 100 caracteres)", 1, 100, "El email");
            String facebook = solicitarStringConLongitud("Ingrese el facebook del padrino (maximo 100 caracteres)", 1, 100, "El facebook");
            String telFijo = solicitarStringConLongitud("Ingrese el telefono fijo (maximo 20 caracteres)", 1, 20, "El telefono fijo");

            String telCelular;
            do {
                telCelular = solicitarStringConLongitud("Ingrese el telefono celular (maximo 20 caracteres)", 1, 20, "El telefono celular");
                
                // Validar que sea distinto al fijo
                if (telCelular.equals(telFijo)) {
                    System.out.println("El telefono celular debe ser distinto al telefono fijo. Intente nuevamente.");
                    telCelular = null;
                }
            } while (telCelular == null);

            // Validacion de fecha de nacimiento
            String fechaNacimientoStr = null;
            LocalDate fechaNacimiento = null;
            while (fechaNacimiento == null) {
                System.out.println("Ingrese la fecha de nacimiento del padrino (AAAA-MM-DD)");
                fechaNacimientoStr = scanner.nextLine().trim();
                if (fechaNacimientoStr.isEmpty()) {
                    System.out.println("La fecha no puede estar vacia. Intente nuevamente.");
                    continue;
                }
                try {
                    fechaNacimiento = LocalDate.parse(fechaNacimientoStr, DateTimeFormatter.ISO_LOCAL_DATE);
                } catch (DateTimeParseException e) {
                    System.out.println("Fecha invalida. Por favor, ingrese la fecha en el formato correcto (AAAA-MM-DD).");
                }
            }

            // Insertar en base de datos
            String sql = "INSERT INTO Padrino (dni, nombre, apellido, direccion, codigoPostal, email, facebook, telFijo, telCelular, fechaNacimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
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
            int filasAfectadas = pstmt.executeUpdate();

            System.out.println("Padrino insertado correctamente.");
            System.out.println("Filas afectadas: " + filasAfectadas);

        } catch (SQLException e) {
            System.err.println("Error al insertar padrino: " + e.getMessage());
        }
    }

    public void eliminarDonante() {
        System.out.println("\n+-+-+-+-+-+-+-+-+-+ ELIMINAR DONANTE +-+-+-+-+-+-+-+-+-+");
        try {
            String dni;
            while (true) {
                System.out.println("Ingrese el dni del donante a eliminar (8 digitos). Ingrese -1 para volver al menu");
                dni = scanner.nextLine();

                if (dni.equals("-1")) {
                    System.out.println("Volviendo al menu...");
                    return;
                }

                if (dni.length() != 8 || !dni.matches("\\d{8}")) { // Revisamos que tenga 8 caracteres que sean solo digitos (usando regex)
                    System.out.println("El DNI debe tener 8 digitos. Intente nuevamente.");
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
                    continue; // volvemos a pedir
                }
                rs.close();
                checkStmt.close();

                // Borramos el donante
                String deleteSQL = "DELETE FROM Donante WHERE dni= ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL);
                deleteStmt.setString(1, dni);
                int filasAfectadas = deleteStmt.executeUpdate();
                deleteStmt.close();

                System.out.println("Filas afectadas: " + filasAfectadas);
                break;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar donante: " + e.getMessage());
        }
    }

    public void listarDonantesYAportes() {
    System.out.println("\n+-+-+-+-+-+-+-+-+-+ LISTAR DONANTES Y APORTES +-+-+-+-+-+-+-+-+-+");
    try {
        String sql =
            "SELECT " +
            "    d.dni, p.nombre, p.apellido, " +
            "    a.nombrePrograma, a.monto, a.frecuencia " +
            "FROM Donante d " +
            "JOIN Aporta a ON d.dni = a.dni " +
            "JOIN Padrino p ON d.dni = p.dni " +
            "ORDER BY p.apellido, p.nombre";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        System.out.printf("%-12s %-15s %-15s %-20s %10s %-10s%n", "DNI", "Nombre", "Apellido", "Programa", "Monto", "Frecuencia");
        while (rs.next()) {
            String dni = rs.getString("dni");
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            String programa = rs.getString("nombrePrograma");
            double monto = rs.getDouble("monto");
            String frecuencia = rs.getString("frecuencia");
            System.out.printf("%-12s %-15s %-15s %-20s %10.2f %-10s%n", dni, nombre, apellido, programa, monto, frecuencia);
        }
    } catch (SQLException e) {
        System.err.println("Error al mostrar los donantes y aportes: " + e.getMessage());
    }
}

    /* ============================= CONSULTAS DEL EJERCICIO 6 ============================= */

    public void mostrarTotalAportesPorProgama() {
        System.out.println("\n+-+-+-+-+-+-+-+-+-+ TOTAL APORTES MENSUALES POR PROGRAMA +-+-+-+-+-+-+-+-+-+\n");
        try {
            String sql = "SELECT nombrePrograma, SUM(monto) AS totalMensual " +
             "FROM Aporta " +
             "WHERE frecuencia = 'Mensual' " +
             "GROUP BY nombrePrograma";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.printf("%-20s %-15s%n", "Programa", "Total Aportado");
            while (rs.next()) {
                System.out.printf("%-20s %-15.2f%n",
                    rs.getString("nombrePrograma"),
                    rs.getDouble("totalMensual")
                );
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.err.println("Error al mostrar el total de aportes por programa: " + e.getMessage());
        }
    }

    public void mostrarDonantesConMasDeDosProgramas() {
        System.out.println("\n+-+-+-+-+-+-+-+-+-+ DONANTES CON MAS DE DOS PROGRAMAS +-+-+-+-+-+-+-+-+-+\n");
        try {
            String sql = "SELECT d.dni, d.cuit, d.ocupacion, p.nombre, p.apellido, COUNT(a.nombrePrograma) AS cantidadProgramas " +
             "FROM Donante d " +
             "JOIN Padrino p ON d.dni = p.dni " +
             "JOIN Aporta a ON d.dni = a.dni " +
             "GROUP BY d.dni, d.cuit, d.ocupacion, p.nombre, p.apellido " +
             "HAVING COUNT(a.nombrePrograma) > 2";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.printf("%-10s %-15s %-15s %-15s %-15s %-20s%n", "DNI", "CUIT", "Nombre", "Apellido", "Ocupacion", "Cantidad Programas");
            while (rs.next()) {
                System.out.printf("%-10s %-15s %-15s %-15s %-15s %-20d%n",
                    rs.getString("dni"),
                    rs.getString("cuit"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("ocupacion"),
                    rs.getInt("cantidadProgramas")
                );
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.err.println("Error al mostrar donantes con mas de dos programas: " + e.getMessage());
        }
    }

    public void mostrarDonantesAportesMensualesConMediosPago() {
        System.out.println("\n+-+-+-+-+-+-+-+-+-+ DONANTES CON APORTES MENSUALES Y MEDIOS DE PAGO +-+-+-+-+-+-+-+-+-+\n");
        try {
            Statement st = conn.createStatement();

            // Dado que los datos de las tarjetas de credito y de los debitos/transferencias son distintos, mostramos separados los aportes de cada tipo
            
            // Tarjetas
            System.out.println("\n>> DONANTES QUE USAN TARJETA DE CRESDITO <<\n");
            String sqlTarjetas = 
                "SELECT d.dni, d.cuit, p.nombre, p.apellido, d.ocupacion, " +
                "a.nombrePrograma, a.monto, a.frecuencia, " +
                "m.nombreTitular, m.apellidoTitular, " +
                "tc.nombreTarjeta, tc.numero, tc.fechaVencimiento " +
                "FROM Donante d " +
                "JOIN Padrino p ON d.dni = p.dni " +
                "JOIN Aporta a ON d.dni = a.dni " +
                "JOIN MedioDePago m ON a.idMP = m.id " +
                "JOIN TarjetaDeCredito tc ON m.id = tc.id " +
                "WHERE a.frecuencia = 'Mensual'";
            ResultSet rs = st.executeQuery(sqlTarjetas);
            System.out.printf("%-10s %-15s %-10s %-10s %-15s %-20s %-10s %-12s %-25s %-15s %-20s %-10s%n",
                             "DNI", "CUIT", "Nombre", "Apellido", "Ocupacion", "Programa", "Monto", "Frecuencia", 
                            "Titular", "Tarjeta", "Nro Tarjeta", "Vencimiento");
            while (rs.next()) {
                String titular = rs.getString("nombreTitular") + " " + rs.getString("apellidoTitular");
                System.out.printf("%-10s %-15s %-10s %-10s %-15s %-20s %-10.2f %-12s %-25s %-15s %-20s %-10s%n",
                    rs.getString("dni"),
                    rs.getString("cuit"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("ocupacion"),
                    rs.getString("nombrePrograma"),
                    rs.getDouble("monto"),
                    rs.getString("frecuencia"),
                    titular,
                    rs.getString("nombreTarjeta"),
                    rs.getString("numero"),
                    rs.getString("fechaVencimiento")
                );
            }
            rs.close();

            // Debito / Transferencia
            System.out.println("\n>> DONANTES QUE USAN DESBITO O TRANSFERENCIA <<\n");
            String sqlDebito = 
                "SELECT d.dni, d.cuit, p.nombre, p.apellido, d.ocupacion, " +
                "a.nombrePrograma, a.monto, a.frecuencia, " + 
                "m.nombreTitular, m.apellidoTitular, " +
                "dt.nombreBanco, dt.sucursalBanco, dt.CBU, dt.tipoCuenta " +
                "FROM Donante d " +
                "JOIN Padrino p ON d.dni = p.dni " +
                "JOIN Aporta a ON d.dni = a.dni " +
                "JOIN MedioDePago m ON a.idMP = m.id " +
                "JOIN DebitoTransferencia dt ON m.id = dt.id " +
                "WHERE a.frecuencia = 'Mensual'";
            rs = st.executeQuery(sqlDebito);
            System.out.printf("%-10s %-15s %-10s %-10s %-15s %-20s %-10s %-12s %-25s %-15s %-25s %-15s%n",
                "DNI", "CUIT", "Nombre", "Apellido", "Ocupacion", "Programa", "Monto", "Frecuencia", 
                "Titular", "Banco", "CBU", "Cuenta");

            while (rs.next()) {
                String titularDebitoTransferencia = rs.getString("nombreTitular") + " " + rs.getString("apellidoTitular");
                System.out.printf("%-10s %-15s %-10s %-10s %-15s %-20s %-10.2f %-12s %-25s %-15s %-25s %-15s%n",
                    rs.getString("dni"),
                    rs.getString("cuit"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("ocupacion"),
                    rs.getString("nombrePrograma"),
                    rs.getDouble("monto"),
                    rs.getString("frecuencia"),
                    titularDebitoTransferencia,
                    rs.getString("nombreBanco"),
                    rs.getString("CBU"),
                    rs.getString("tipoCuenta")
                );
            }
            rs.close();

            st.close();
        } catch (SQLException e) {
            System.err.println("Error al mostrar donantes con aportes mensuales y medios de pago: " + e.getMessage());
        }
    }

    /* ============================= CONSULTAS VARIAS (no pedidas en la consigna) ============================= */

    // No se pide, pero sirve para poder ver si insertar padrino funciona bien
    public void mostrarTodosPadrinos() {
        System.out.println("\n+-+-+-+-+-+-+-+-+-+ PADRINOS +-+-+-+-+-+-+-+-+-+");
        try {
            String sql = "SELECT * FROM Padrino ORDER BY apellido, nombre";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            System.out.printf("%-10s %-15s %-15s %-25s %-12s %-25s %-20s %-15s %-15s %-15s%n",
                "DNI", "Nombre", "Apellido", "Direccion", "Cod. Postal", "Email", "Facebook", "Tel. Fijo", "Tel. Celular", "F. Nacimiento");
            
            while (rs.next()) {
                System.out.printf("%-10s %-15s %-15s %-25s %-12s %-25s %-20s %-15s %-15s %-15s%n",
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("direccion"),
                    rs.getString("codigoPostal"),
                    rs.getString("email"),
                    rs.getString("facebook"),
                    rs.getString("telFijo"),
                    rs.getString("telCelular"),
                    rs.getDate("fechaNacimiento")
                );
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.err.println("Error al mostrar los padrinos: " + e.getMessage());
        }
    }

    // Para poder ver si se eliminaron correctamente los donantes
    public void mostrarAuditoriaEliminacionDonantes() {
        System.out.println("\n+-+-+-+-+-+-+-+-+-+ AUDITORÍA ELIMINACION DONANTES +-+-+-+-+-+-+-+-+-+");
        try {
            String sql = "SELECT * FROM AuditoriaEliminacionDonante ORDER BY fechaEliminacion DESC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            System.out.printf("%-5s %-10s %-15s %-15s %-20s %-20s%n",
                "ID", "DNI", "Nombre", "Apellido", "Fecha Eliminacion", "Usuario DB");
            
            while (rs.next()) {
                System.out.printf("%-5d %-10s %-15s %-15s %-20s %-20s%n",
                    rs.getInt("id"),
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getTimestamp("fechaEliminacion"),
                    rs.getString("usuarioDB")
                );
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.err.println("Error al mostrar la auditoria de eliminacion de donantes: " + e.getMessage());
        }
    }

    /* ============================= AUXILIARES ============================= */

    // Se encarga de pedir un string, mostrando el mensaje dado, y asegurandose de que el string ingresado respete el rango de longitud.
    private String solicitarStringConLongitud(String mensaje, int minLength, int maxLength, String nombreCampo) {
        String input;
        do {
            System.out.println(mensaje);
            input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                System.out.println(nombreCampo + " no puede estar vacio. Intente nuevamente.");
                input = null;
            } else if (input.length() < minLength || input.length() > maxLength) {
                if (minLength == maxLength) {
                    System.out.println(nombreCampo + " debe tener exactamente " + maxLength + " caracteres. Intente nuevamente.");
                } else {
                    System.out.println(nombreCampo + " debe tener entre " + minLength + " y " + maxLength + " caracteres. Intente nuevamente.");
                }
                input = null;
            }
        } while (input == null);
        return input;
    }

    public void cerrarScanner() {
        scanner.close();
    }

}
