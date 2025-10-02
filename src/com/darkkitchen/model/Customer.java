package com.darkkitchen.model;

/**
 * Modelo que representa un cliente en el sistema Dark Kitchen
 */
public class Customer {
    
    private int customerId;
    private String fullName;
    private String email;
    private String phone;
    private String deliveryAddress;
    private java.sql.Timestamp registrationDate;
    private boolean isActive;
    
    // Constructor vacío
    public Customer() {}
    
    // Constructor con parámetros
    public Customer(String fullName, String email, String phone, String deliveryAddress) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.deliveryAddress = deliveryAddress;
        this.isActive = true;
    }
    
    // Constructor completo
    public Customer(int customerId, String fullName, String email, String phone, 
                   String deliveryAddress, java.sql.Timestamp registrationDate, boolean isActive) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.deliveryAddress = deliveryAddress;
        this.registrationDate = registrationDate;
        this.isActive = isActive;
    }
    
    // Getters y Setters
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    
    public java.sql.Timestamp getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(java.sql.Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", registrationDate=" + registrationDate +
                ", isActive=" + isActive +
                '}';
    }
}