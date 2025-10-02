package com.darkkitchen.model;

import java.math.BigDecimal;

/**
 * Modelo que representa un platillo en el menú
 */
public class Dish {
    
    private int dishId;
    private int categoryId;
    private String name;
    private String description;
    private BigDecimal price;
    private int preparationTime; // en minutos
    private boolean isAvailable;
    private boolean isVegetarian;
    private boolean isSpicy;
    private java.sql.Timestamp createdAt;
    
    // Para mostrar el nombre de la categoría (no se guarda en BD)
    private String categoryName;
    
    // Constructor vacío
    public Dish() {}
    
    // Constructor con parámetros básicos
    public Dish(int categoryId, String name, String description, BigDecimal price, 
               int preparationTime, boolean isVegetarian, boolean isSpicy) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.preparationTime = preparationTime;
        this.isVegetarian = isVegetarian;
        this.isSpicy = isSpicy;
        this.isAvailable = true;
    }
    
    // Constructor completo
    public Dish(int dishId, int categoryId, String name, String description, 
               BigDecimal price, int preparationTime, boolean isAvailable,
               boolean isVegetarian, boolean isSpicy, java.sql.Timestamp createdAt) {
        this.dishId = dishId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.preparationTime = preparationTime;
        this.isAvailable = isAvailable;
        this.isVegetarian = isVegetarian;
        this.isSpicy = isSpicy;
        this.createdAt = createdAt;
    }
    
    // Getters y Setters
    public int getDishId() {
        return dishId;
    }
    
    public void setDishId(int dishId) {
        this.dishId = dishId;
    }
    
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
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public int getPreparationTime() {
        return preparationTime;
    }
    
    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    
    public boolean isVegetarian() {
        return isVegetarian;
    }
    
    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }
    
    public boolean isSpicy() {
        return isSpicy;
    }
    
    public void setSpicy(boolean spicy) {
        isSpicy = spicy;
    }
    
    public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    @Override
    public String toString() {
        return name + " - $" + price;
    }
}