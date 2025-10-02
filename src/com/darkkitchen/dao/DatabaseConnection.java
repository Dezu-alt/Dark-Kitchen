package com.darkkitchen.dao;

import java.sql.*;

/**
 * Clase para manejar la conexión a la base de datos MySQL
 * Implementa el patrón Singleton para garantizar una sola conexión
 */
public class DatabaseConnection {
    
    // Instancia singleton
    private static DatabaseConnection instance;
    
    // Configuración de conexión
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PORT = "3306";
    private static final String DEFAULT_DATABASE = "dark_kitchen";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "Em1liano"; 
    
    // Configuración actual
    private String host;
    private String port;
    private String database;
    private String username;
    private String password;
    private String url;
    
    // Instancia única de la conexión
    private static Connection connection = null;
    
    /**
     * Constructor privado para implementar Singleton
     */
    private DatabaseConnection() {
        // Inicializar con valores por defecto
        this.host = DEFAULT_HOST;
        this.port = DEFAULT_PORT;
        this.database = DEFAULT_DATABASE;
        this.username = DEFAULT_USERNAME;
        this.password = DEFAULT_PASSWORD;
        this.url = buildUrl();
    }
    
    /**
     * Obtiene la instancia singleton de DatabaseConnection
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    /**
     * Construye la URL de conexión
     */
    private String buildUrl() {
        return "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&serverTimezone=UTC";
    }
    
    /**
     * Obtiene la conexión a la base de datos
     * @return Connection objeto de conexión
     * @throws SQLException si hay error en la conexión
     */
    public static Connection getConnection() throws SQLException {
        DatabaseConnection dbInstance = getInstance();
        if (connection == null || connection.isClosed()) {
            try {
                // Cargar el driver MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(dbInstance.url, dbInstance.username, dbInstance.password);
                System.out.println("✅ Conexión exitosa a la base de datos");
            } catch (ClassNotFoundException e) {
                System.err.println("❌ Error: No se encontró el driver MySQL");
                System.err.println("Asegúrate de tener mysql-connector-java.jar en la carpeta lib/");
                throw new SQLException("Driver MySQL no encontrado", e);
            } catch (SQLException e) {
                System.err.println("❌ Error al conectar a la base de datos: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }
    
    /**
     * Cierra la conexión a la base de datos
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔌 Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al cerrar la conexión: " + e.getMessage());
        }
    }
    
    /**
     * Prueba la conexión a la base de datos
     * @return true si la conexión es exitosa, false en caso contrario
     */
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            if (conn != null && !conn.isClosed()) {
                // Ejecutar una consulta simple para verificar la conexión
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT 1");
                rs.close();
                stmt.close();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error en test de conexión: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Configura los parámetros de conexión personalizados
     */
    public void setConnectionParams(String host, String port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.url = buildUrl();
        
        // Cerrar conexión existente para usar los nuevos parámetros
        closeConnection();
    }
    
    /**
     * Obtiene la configuración actual de conexión
     */
    public String getConnectionInfo() {
        return String.format("Host: %s:%s | Database: %s | User: %s", 
                           host, port, database, username);
    }
    
    /**
     * Getters para acceder a la configuración
     */
    public String getHost() { return host; }
    public String getPort() { return port; }
    public String getDatabase() { return database; }
    public String getUsername() { return username; }
    public String getUrl() { return url; }
}