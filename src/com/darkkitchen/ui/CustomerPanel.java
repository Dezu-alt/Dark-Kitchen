package com.darkkitchen.ui;

import com.darkkitchen.dao.CustomerDAO;
import com.darkkitchen.model.Customer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Panel para gestionar operaciones CRUD de clientes
 */
public class CustomerPanel extends JPanel {
    
    private CustomerDAO customerDAO;
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, emailField, phoneField, addressField, searchField;
    private JCheckBox activeCheckBox;
    private JButton addButton, updateButton, deleteButton, clearButton, searchButton;
    private Customer selectedCustomer;
    
    public CustomerPanel() {
        customerDAO = new CustomerDAO();
        initializeComponents();
        setupLayout();
        setupEventListeners();
        refreshData();
    }
    
    private void initializeComponents() {
        // Campos de texto
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(15);
        addressField = new JTextField(30);
        searchField = new JTextField(20);
        activeCheckBox = new JCheckBox("Activo", true);
        
        // Botones
        addButton = new JButton("Agregar");
        updateButton = new JButton("Actualizar");
        deleteButton = new JButton("Eliminar");
        clearButton = new JButton("Limpiar");
        searchButton = new JButton("Buscar");
        
        // Configurar botones
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        
        // Tabla
        String[] columnNames = {"ID", "Nombre", "Email", "Teléfono", "Dirección", "Fecha Registro", "Activo"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer tabla de solo lectura
            }
        };
        
        customerTable = new JTable(tableModel);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customerTable.setRowHeight(25);
        customerTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Configurar anchos de columnas
        customerTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        customerTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Nombre
        customerTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Email
        customerTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Teléfono
        customerTable.getColumnModel().getColumn(4).setPreferredWidth(250); // Dirección
        customerTable.getColumnModel().getColumn(5).setPreferredWidth(120); // Fecha
        customerTable.getColumnModel().getColumn(6).setPreferredWidth(60);  // Activo
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior - Búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Buscar Cliente"));
        searchPanel.add(new JLabel("Nombre:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        // Panel central - Tabla
        JScrollPane scrollPane = new JScrollPane(customerTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Clientes"));
        
        // Panel inferior - Formulario
        JPanel formPanel = createFormPanel();
        
        // Agregar componentes al panel principal
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Fila 1
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 3;
        formPanel.add(emailField, gbc);
        
        // Fila 2
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);
        
        gbc.gridx = 2;
        formPanel.add(activeCheckBox, gbc);
        
        // Fila 3
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(addressField, gbc);
        
        // Fila 4 - Botones
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        gbc.gridwidth = 4;
        formPanel.add(buttonPanel, gbc);
        
        return formPanel;
    }
    
    private void setupEventListeners() {
        // Listener para selección de tabla
        customerTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow >= 0) {
                    loadCustomerFromTable(selectedRow);
                }
            }
        });
        
        // Listeners para botones
        addButton.addActionListener(e -> addCustomer());
        updateButton.addActionListener(e -> updateCustomer());
        deleteButton.addActionListener(e -> deleteCustomer());
        clearButton.addActionListener(e -> clearForm());
        searchButton.addActionListener(e -> searchCustomers());
        
        // Enter en campo de búsqueda
        searchField.addActionListener(e -> searchCustomers());
    }
    
    private void loadCustomerFromTable(int row) {
        int customerId = (Integer) tableModel.getValueAt(row, 0);
        selectedCustomer = customerDAO.readById(customerId);
        
        if (selectedCustomer != null) {
            nameField.setText(selectedCustomer.getFullName());
            emailField.setText(selectedCustomer.getEmail());
            phoneField.setText(selectedCustomer.getPhone());
            addressField.setText(selectedCustomer.getDeliveryAddress());
            activeCheckBox.setSelected(selectedCustomer.isActive());
            
            updateButton.setEnabled(true);
            deleteButton.setEnabled(true);
            addButton.setEnabled(false);
        }
    }
    
    private void addCustomer() {
        if (validateForm()) {
            Customer customer = new Customer(
                nameField.getText().trim(),
                emailField.getText().trim(),
                phoneField.getText().trim(),
                addressField.getText().trim()
            );
            customer.setActive(activeCheckBox.isSelected());
            
            if (customerDAO.create(customer)) {
                JOptionPane.showMessageDialog(this, "Cliente agregado exitosamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                refreshData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar cliente", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void updateCustomer() {
        if (selectedCustomer != null && validateForm()) {
            selectedCustomer.setFullName(nameField.getText().trim());
            selectedCustomer.setEmail(emailField.getText().trim());
            selectedCustomer.setPhone(phoneField.getText().trim());
            selectedCustomer.setDeliveryAddress(addressField.getText().trim());
            selectedCustomer.setActive(activeCheckBox.isSelected());
            
            if (customerDAO.update(selectedCustomer)) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                refreshData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar cliente", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteCustomer() {
        if (selectedCustomer != null) {
            int option = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres eliminar a " + selectedCustomer.getFullName() + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (option == JOptionPane.YES_OPTION) {
                if (customerDAO.delete(selectedCustomer.getCustomerId())) {
                    JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    refreshData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar cliente", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        activeCheckBox.setSelected(true);
        
        selectedCustomer = null;
        customerTable.clearSelection();
        
        addButton.setEnabled(true);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }
    
    private void searchCustomers() {
        String searchTerm = searchField.getText().trim();
        List<Customer> customers;
        
        if (searchTerm.isEmpty()) {
            customers = customerDAO.readAll();
        } else {
            customers = customerDAO.searchByName(searchTerm);
        }
        
        loadCustomersToTable(customers);
        clearForm();
    }
    
    public void refreshData() {
        List<Customer> customers = customerDAO.readAll();
        loadCustomersToTable(customers);
        clearForm();
        searchField.setText("");
    }
    
    private void loadCustomersToTable(List<Customer> customers) {
        tableModel.setRowCount(0); // Limpiar tabla
        
        for (Customer customer : customers) {
            Object[] row = {
                customer.getCustomerId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getDeliveryAddress(),
                customer.getRegistrationDate() != null ? 
                    customer.getRegistrationDate().toString().substring(0, 19) : "N/A",
                customer.isActive() ? "Sí" : "No"
            };
            tableModel.addRow(row);
        }
    }
    
    private boolean validateForm() {
        StringBuilder errors = new StringBuilder();
        
        if (nameField.getText().trim().isEmpty()) {
            errors.append("• El nombre es obligatorio\n");
        }
        
        if (emailField.getText().trim().isEmpty()) {
            errors.append("• El email es obligatorio\n");
        } else if (!emailField.getText().contains("@")) {
            errors.append("• El email debe tener un formato válido\n");
        }
        
        if (phoneField.getText().trim().isEmpty()) {
            errors.append("• El teléfono es obligatorio\n");
        }
        
        if (addressField.getText().trim().isEmpty()) {
            errors.append("• La dirección es obligatoria\n");
        }
        
        if (errors.length() > 0) {
            JOptionPane.showMessageDialog(this, 
                "❌ Por favor corrige los siguientes errores:\n\n" + errors.toString(),
                "Errores de Validación", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
}