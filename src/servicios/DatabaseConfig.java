package servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esta clase se encarga de la configuracion de la conexion a la base de datos
 */
public class DatabaseConfig {
    // Configuracion de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/Donaciones";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "tuContraseña";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            System.out.println("¡Conexion exitosa!");
            closeConnection(conn);
        } catch (SQLException e) {
            System.err.println("Error de conexion: " + e.getMessage());
        }
    }
    // Método para conectarse a la base de datos
    public static Connection getConnection() throws SQLException {
        try {
            // Cargar el driver si no está cargado
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver no encontrado: " + e.getMessage());
        }
    }
    
    // Método para cerrar la conexión
    public static void closeConnection(Connection conn) {
        // Si hay una conexion:
        if (conn != null) {
            // Intentamos cerrar la conexión
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
    }
}