package com.darkkitchen.model;

/**
 * Modelo que representa una categoría de platillos
 */
public class Category {
    
    private int categoryId;
    private String name;
    private String description;
    private boolean isActive;
    private java.sql.Timestamp createdAt;
    
    // Constructor vacío
    public Category() {}
    
    // Constructor con parámetros básicos
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
        this.isActive = true;
    }
    
    // Constructor completo
    public Category(int categoryId, String name, String description, 
                   boolean isActive, java.sql.Timestamp createdAt) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }
    
    // Getters y Setters
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return name; // Para mostrar en ComboBox y listas
    }
}