package com.darkkitchen.dao;

import com.darkkitchen.model.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para manejar operaciones CRUD de Category
 */
public class CategoryDAO {
    
    /**
     * Crear una nueva categoría
     */
    public boolean create(Category category) {
        String sql = "INSERT INTO Category (name, description) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    category.setCategoryId(generatedKeys.getInt(1));
                }
                System.out.println("✅ Categoría creada exitosamente: " + category.getName());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al crear categoría: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Obtener todas las categorías activas
     */
    public List<Category> readAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Category WHERE active = TRUE ORDER BY name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Category category = new Category(
                    rs.getInt("category_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getBoolean("active"),
                    rs.getTimestamp("created_at")
                );
                categories.add(category);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener categorías: " + e.getMessage());
        }
        return categories;
    }
    
    /**
     * Obtener categoría por ID
     */
    public Category readById(int categoryId) {
        String sql = "SELECT * FROM Category WHERE category_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Category(
                    rs.getInt("category_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getBoolean("active"),
                    rs.getTimestamp("created_at")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener categoría por ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Actualizar categoría
     */
    public boolean update(Category category) {
        String sql = "UPDATE Category SET name = ?, description = ?, active = ? WHERE category_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setBoolean(3, category.isActive());
            stmt.setInt(4, category.getCategoryId());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Categoría actualizada exitosamente: " + category.getName());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar categoría: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Eliminar categoría (marca como inactiva)
     */
    public boolean delete(int categoryId) {
        String sql = "UPDATE Category SET active = FALSE WHERE category_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, categoryId);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Categoría desactivada exitosamente con ID: " + categoryId);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al desactivar categoría: " + e.getMessage());
        }
        return false;
    }
}