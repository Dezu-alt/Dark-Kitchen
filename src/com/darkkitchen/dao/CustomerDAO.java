package com.darkkitchen.dao;

import com.darkkitchen.model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para manejar operaciones CRUD de Customer
 */
public class CustomerDAO {
    
    /**
     * Crear un nuevo cliente
     * @param customer Cliente a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public boolean create(Customer customer) {
        String sql = "INSERT INTO Customer (full_name, email, phone) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, customer.getFullName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Obtener el ID generado
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    customer.setCustomerId(generatedKeys.getInt(1));
                }
                System.out.println("✅ Cliente creado exitosamente: " + customer.getFullName());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al crear cliente: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Obtener todos los clientes
     * @return Lista de todos los clientes
     */
    public List<Customer> readAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer ORDER BY full_name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Customer customer = new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    null, // delivery_address no existe en BD
                    rs.getTimestamp("registration_date"),
                    rs.getBoolean("active")
                );
                customers.add(customer);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener clientes: " + e.getMessage());
        }
        return customers;
    }
    
    /**
     * Obtener un cliente por ID
     * @param customerId ID del cliente
     * @return Cliente encontrado o null
     */
    public Customer readById(int customerId) {
        String sql = "SELECT * FROM Customer WHERE customer_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    null, // delivery_address no existe en BD
                    rs.getTimestamp("registration_date"),
                    rs.getBoolean("active")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener cliente por ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Actualizar un cliente existente
     * @param customer Cliente con datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean update(Customer customer) {
        String sql = "UPDATE Customer SET full_name = ?, email = ?, phone = ?, active = ? WHERE customer_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, customer.getFullName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setBoolean(4, customer.isActive());
            stmt.setInt(5, customer.getCustomerId());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Cliente actualizado exitosamente: " + customer.getFullName());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar cliente: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Eliminar un cliente por ID
     * @param customerId ID del cliente a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean delete(int customerId) {
        String sql = "DELETE FROM Customer WHERE customer_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, customerId);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Cliente eliminado exitosamente con ID: " + customerId);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar cliente: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Buscar clientes por nombre
     * @param name Nombre a buscar (parcial)
     * @return Lista de clientes que coinciden
     */
    public List<Customer> searchByName(String name) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer WHERE full_name LIKE ? ORDER BY full_name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Customer customer = new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    null, // delivery_address no existe en BD
                    rs.getTimestamp("registration_date"),
                    rs.getBoolean("active")
                );
                customers.add(customer);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar clientes por nombre: " + e.getMessage());
        }
        return customers;
    }
}