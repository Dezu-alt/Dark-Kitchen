package com.darkkitchen.dao;

import com.darkkitchen.model.Dish;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para manejar operaciones CRUD de Dish
 */
public class DishDAO {
    
    /**
     * Crear un nuevo platillo
     */
    public boolean create(Dish dish) {
        String sql = "INSERT INTO Dish (brand_id, category_id, name, description, price, preparation_time) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, 1); // brand_id por defecto (podrías agregar parámetro)
            stmt.setInt(2, dish.getCategoryId());
            stmt.setString(3, dish.getName());
            stmt.setString(4, dish.getDescription());
            stmt.setBigDecimal(5, dish.getPrice());
            stmt.setInt(6, dish.getPreparationTime());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    dish.setDishId(generatedKeys.getInt(1));
                }
                System.out.println("✅ Platillo creado exitosamente: " + dish.getName());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al crear platillo: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Obtener todos los platillos con información de categoría
     */
    public List<Dish> readAll() {
        List<Dish> dishes = new ArrayList<>();
        String sql = """
            SELECT d.*, c.name as category_name 
            FROM Dish d 
            INNER JOIN Category c ON d.category_id = c.category_id 
            WHERE d.active = TRUE 
            ORDER BY c.name, d.name
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Dish dish = new Dish(
                    rs.getInt("dish_id"),
                    rs.getInt("category_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getBigDecimal("price"),
                    rs.getInt("preparation_time"),
                    rs.getBoolean("active"),
                    false, // vegetarian por defecto
                    false, // spicy por defecto
                    rs.getTimestamp("created_at")
                );
                dish.setCategoryName(rs.getString("category_name"));
                dishes.add(dish);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener platillos: " + e.getMessage());
        }
        return dishes;
    }
    
    /**
     * Obtener platillo por ID
     */
    public Dish readById(int dishId) {
        String sql = """
            SELECT d.*, c.name as category_name 
            FROM Dish d 
            INNER JOIN Category c ON d.category_id = c.category_id 
            WHERE d.dish_id = ?
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, dishId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Dish dish = new Dish(
                    rs.getInt("dish_id"),
                    rs.getInt("category_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getBigDecimal("price"),
                    rs.getInt("preparation_time"),
                    rs.getBoolean("active"),
                    false, // vegetarian por defecto
                    false, // spicy por defecto
                    rs.getTimestamp("created_at")
                );
                dish.setCategoryName(rs.getString("category_name"));
                return dish;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener platillo por ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Obtener platillos por categoría
     */
    public List<Dish> readByCategory(int categoryId) {
        List<Dish> dishes = new ArrayList<>();
        String sql = """
            SELECT d.*, c.name as category_name 
            FROM Dish d 
            INNER JOIN Category c ON d.category_id = c.category_id 
            WHERE d.category_id = ? AND d.active = TRUE 
            ORDER BY d.name
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Dish dish = new Dish(
                    rs.getInt("dish_id"),
                    rs.getInt("category_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getBigDecimal("price"),
                    rs.getInt("preparation_time"),
                    rs.getBoolean("available"),
                    rs.getBoolean("vegetarian"),
                    rs.getBoolean("spicy"),
                    rs.getTimestamp("created_at")
                );
                dish.setCategoryName(rs.getString("category_name"));
                dishes.add(dish);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener platillos por categoría: " + e.getMessage());
        }
        return dishes;
    }
    
    /**
     * Actualizar platillo
     */
    public boolean update(Dish dish) {
        String sql = "UPDATE Dish SET brand_id = ?, category_id = ?, name = ?, description = ?, price = ?, preparation_time = ?, active = ? WHERE dish_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, 1); // brand_id por defecto
            stmt.setInt(2, dish.getCategoryId());
            stmt.setString(3, dish.getName());
            stmt.setString(4, dish.getDescription());
            stmt.setBigDecimal(5, dish.getPrice());
            stmt.setInt(6, dish.getPreparationTime());
            stmt.setBoolean(7, dish.isAvailable());
            stmt.setInt(8, dish.getDishId());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Platillo actualizado exitosamente: " + dish.getName());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar platillo: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Eliminar platillo (marca como no disponible)
     */
    public boolean delete(int dishId) {
        String sql = "UPDATE Dish SET available = FALSE WHERE dish_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, dishId);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Platillo marcado como no disponible con ID: " + dishId);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al marcar platillo como no disponible: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Buscar platillos por nombre
     */
    public List<Dish> searchByName(String name) {
        List<Dish> dishes = new ArrayList<>();
        String sql = """
            SELECT d.*, c.name as category_name 
            FROM Dish d 
            INNER JOIN Category c ON d.category_id = c.category_id 
            WHERE d.name LIKE ? AND d.available = TRUE 
            ORDER BY d.name
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Dish dish = new Dish(
                    rs.getInt("dish_id"),
                    rs.getInt("category_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getBigDecimal("price"),
                    rs.getInt("preparation_time"),
                    rs.getBoolean("available"),
                    rs.getBoolean("vegetarian"),
                    rs.getBoolean("spicy"),
                    rs.getTimestamp("created_at")
                );
                dish.setCategoryName(rs.getString("category_name"));
                dishes.add(dish);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar platillos por nombre: " + e.getMessage());
        }
        return dishes;
    }
}