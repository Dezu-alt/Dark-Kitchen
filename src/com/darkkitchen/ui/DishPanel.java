package com.darkkitchen.ui;

import com.darkkitchen.dao.CategoryDAO;
import com.darkkitchen.dao.DishDAO;
import com.darkkitchen.model.Category;
import com.darkkitchen.model.Dish;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Panel para gestionar operaciones CRUD de platillos y categorías
 */
public class DishPanel extends JPanel {
    
    private DishDAO dishDAO;
    private CategoryDAO categoryDAO;
    private JTable dishTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, descriptionField, priceField, prepTimeField, searchField;
    private JComboBox<Category> categoryComboBox;
    private JCheckBox availableCheckBox, vegetarianCheckBox, spicyCheckBox;
    private JButton addButton, updateButton, deleteButton, clearButton, searchButton;
    private JButton manageCategoriesButton;
    private Dish selectedDish;
    
    public DishPanel() {
        dishDAO = new DishDAO();
        categoryDAO = new CategoryDAO();
        initializeComponents();
        setupLayout();
        setupEventListeners();
        refreshData();
    }
    
    private void initializeComponents() {
        // Campos de texto
        nameField = new JTextField(20);
        descriptionField = new JTextField(30);
        priceField = new JTextField(10);
        prepTimeField = new JTextField(5);
        searchField = new JTextField(20);
        
        // ComboBox para categorías
        categoryComboBox = new JComboBox<>();
        loadCategories();
        
        // CheckBoxes
        availableCheckBox = new JCheckBox("Disponible", true);
        vegetarianCheckBox = new JCheckBox("Vegetariano", false);
        spicyCheckBox = new JCheckBox("Picante", false);
        
        // Botones
        addButton = new JButton("➕ Agregar");
        updateButton = new JButton("✏️ Actualizar");
        deleteButton = new JButton("🗑️ Eliminar");
        clearButton = new JButton("🧹 Limpiar");
        searchButton = new JButton("🔍 Buscar");
        manageCategoriesButton = new JButton("📂 Gestionar Categorías");
        
        // Configurar botones
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        
        // Tabla
        String[] columnNames = {"ID", "Nombre", "Categoría", "Descripción", "Precio", 
                               "Tiempo (min)", "Disponible", "Vegetariano", "Picante"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        dishTable = new JTable(tableModel);
        dishTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dishTable.setRowHeight(25);
        dishTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Configurar anchos de columnas
        dishTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        dishTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Nombre
        dishTable.getColumnModel().getColumn(2).setPreferredWidth(120); // Categoría
        dishTable.getColumnModel().getColumn(3).setPreferredWidth(200); // Descripción
        dishTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Precio
        dishTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Tiempo
        dishTable.getColumnModel().getColumn(6).setPreferredWidth(80);  // Disponible
        dishTable.getColumnModel().getColumn(7).setPreferredWidth(80);  // Vegetariano
        dishTable.getColumnModel().getColumn(8).setPreferredWidth(70);  // Picante
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior - Búsqueda y gestión de categorías
        JPanel topPanel = new JPanel(new BorderLayout());
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createTitledBorder("🔍 Buscar Platillo"));
        searchPanel.add(new JLabel("Nombre:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        JPanel categoryManagementPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        categoryManagementPanel.add(manageCategoriesButton);
        
        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(categoryManagementPanel, BorderLayout.EAST);
        
        // Panel central - Tabla
        JScrollPane scrollPane = new JScrollPane(dishTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("🍽️ Lista de Platillos"));
        
        // Panel inferior - Formulario
        JPanel formPanel = createFormPanel();
        
        // Agregar componentes al panel principal
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("📝 Datos del Platillo"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Fila 1
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Categoría:"), gbc);
        gbc.gridx = 3;
        formPanel.add(categoryComboBox, gbc);
        
        // Fila 2
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Precio ($):"), gbc);
        gbc.gridx = 1;
        formPanel.add(priceField, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Tiempo (min):"), gbc);
        gbc.gridx = 3;
        formPanel.add(prepTimeField, gbc);
        
        // Fila 3
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(descriptionField, gbc);
        
        // Fila 4 - CheckBoxes
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(availableCheckBox, gbc);
        gbc.gridx = 1;
        formPanel.add(vegetarianCheckBox, gbc);
        gbc.gridx = 2;
        formPanel.add(spicyCheckBox, gbc);
        
        // Fila 5 - Botones
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        formPanel.add(buttonPanel, gbc);
        
        return formPanel;
    }
    
    private void setupEventListeners() {
        // Listener para selección de tabla
        dishTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = dishTable.getSelectedRow();
                if (selectedRow >= 0) {
                    loadDishFromTable(selectedRow);
                }
            }
        });
        
        // Listeners para botones
        addButton.addActionListener(e -> addDish());
        updateButton.addActionListener(e -> updateDish());
        deleteButton.addActionListener(e -> deleteDish());
        clearButton.addActionListener(e -> clearForm());
        searchButton.addActionListener(e -> searchDishes());
        manageCategoriesButton.addActionListener(e -> manageCategoriesDialog());
        
        // Enter en campo de búsqueda
        searchField.addActionListener(e -> searchDishes());
    }
    
    private void loadCategories() {
        categoryComboBox.removeAllItems();
        List<Category> categories = categoryDAO.readAll();
        for (Category category : categories) {
            categoryComboBox.addItem(category);
        }
    }
    
    private void loadDishFromTable(int row) {
        int dishId = (Integer) tableModel.getValueAt(row, 0);
        selectedDish = dishDAO.readById(dishId);
        
        if (selectedDish != null) {
            nameField.setText(selectedDish.getName());
            descriptionField.setText(selectedDish.getDescription());
            priceField.setText(selectedDish.getPrice().toString());
            prepTimeField.setText(String.valueOf(selectedDish.getPreparationTime()));
            availableCheckBox.setSelected(selectedDish.isAvailable());
            vegetarianCheckBox.setSelected(selectedDish.isVegetarian());
            spicyCheckBox.setSelected(selectedDish.isSpicy());
            
            // Seleccionar categoría en ComboBox
            for (int i = 0; i < categoryComboBox.getItemCount(); i++) {
                Category category = categoryComboBox.getItemAt(i);
                if (category.getCategoryId() == selectedDish.getCategoryId()) {
                    categoryComboBox.setSelectedIndex(i);
                    break;
                }
            }
            
            updateButton.setEnabled(true);
            deleteButton.setEnabled(true);
            addButton.setEnabled(false);
        }
    }
    
    private void addDish() {
        if (validateForm()) {
            Category selectedCategory = (Category) categoryComboBox.getSelectedItem();
            
            Dish dish = new Dish(
                selectedCategory.getCategoryId(),
                nameField.getText().trim(),
                descriptionField.getText().trim(),
                new BigDecimal(priceField.getText().trim()),
                Integer.parseInt(prepTimeField.getText().trim()),
                vegetarianCheckBox.isSelected(),
                spicyCheckBox.isSelected()
            );
            dish.setAvailable(availableCheckBox.isSelected());
            
            if (dishDAO.create(dish)) {
                JOptionPane.showMessageDialog(this, "✅ Platillo agregado exitosamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                refreshData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al agregar platillo", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void updateDish() {
        if (selectedDish != null && validateForm()) {
            Category selectedCategory = (Category) categoryComboBox.getSelectedItem();
            
            selectedDish.setCategoryId(selectedCategory.getCategoryId());
            selectedDish.setName(nameField.getText().trim());
            selectedDish.setDescription(descriptionField.getText().trim());
            selectedDish.setPrice(new BigDecimal(priceField.getText().trim()));
            selectedDish.setPreparationTime(Integer.parseInt(prepTimeField.getText().trim()));
            selectedDish.setAvailable(availableCheckBox.isSelected());
            selectedDish.setVegetarian(vegetarianCheckBox.isSelected());
            selectedDish.setSpicy(spicyCheckBox.isSelected());
            
            if (dishDAO.update(selectedDish)) {
                JOptionPane.showMessageDialog(this, "✅ Platillo actualizado exitosamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                refreshData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al actualizar platillo", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteDish() {
        if (selectedDish != null) {
            int option = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres marcar como no disponible: " + selectedDish.getName() + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (option == JOptionPane.YES_OPTION) {
                if (dishDAO.delete(selectedDish.getDishId())) {
                    JOptionPane.showMessageDialog(this, "✅ Platillo marcado como no disponible", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    refreshData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error al eliminar platillo", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void clearForm() {
        nameField.setText("");
        descriptionField.setText("");
        priceField.setText("");
        prepTimeField.setText("");
        availableCheckBox.setSelected(true);
        vegetarianCheckBox.setSelected(false);
        spicyCheckBox.setSelected(false);
        
        if (categoryComboBox.getItemCount() > 0) {
            categoryComboBox.setSelectedIndex(0);
        }
        
        selectedDish = null;
        dishTable.clearSelection();
        
        addButton.setEnabled(true);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }
    
    private void searchDishes() {
        String searchTerm = searchField.getText().trim();
        List<Dish> dishes;
        
        if (searchTerm.isEmpty()) {
            dishes = dishDAO.readAll();
        } else {
            dishes = dishDAO.searchByName(searchTerm);
        }
        
        loadDishesToTable(dishes);
        clearForm();
    }
    
    public void refreshData() {
        loadCategories();
        List<Dish> dishes = dishDAO.readAll();
        loadDishesToTable(dishes);
        clearForm();
        searchField.setText("");
    }
    
    private void loadDishesToTable(List<Dish> dishes) {
        tableModel.setRowCount(0);
        
        for (Dish dish : dishes) {
            Object[] row = {
                dish.getDishId(),
                dish.getName(),
                dish.getCategoryName(),
                dish.getDescription(),
                "$" + dish.getPrice(),
                dish.getPreparationTime() + " min",
                dish.isAvailable() ? "Sí" : "No",
                dish.isVegetarian() ? "Sí" : "No",
                dish.isSpicy() ? "Sí" : "No"
            };
            tableModel.addRow(row);
        }
    }
    
    private boolean validateForm() {
        StringBuilder errors = new StringBuilder();
        
        if (nameField.getText().trim().isEmpty()) {
            errors.append("• El nombre es obligatorio\n");
        }
        
        if (descriptionField.getText().trim().isEmpty()) {
            errors.append("• La descripción es obligatoria\n");
        }
        
        try {
            BigDecimal price = new BigDecimal(priceField.getText().trim());
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                errors.append("• El precio debe ser mayor a 0\n");
            }
        } catch (NumberFormatException e) {
            errors.append("• El precio debe ser un número válido\n");
        }
        
        try {
            int prepTime = Integer.parseInt(prepTimeField.getText().trim());
            if (prepTime <= 0) {
                errors.append("• El tiempo de preparación debe ser mayor a 0\n");
            }
        } catch (NumberFormatException e) {
            errors.append("• El tiempo de preparación debe ser un número entero\n");
        }
        
        if (categoryComboBox.getSelectedItem() == null) {
            errors.append("• Debe seleccionar una categoría\n");
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
    
    private void manageCategoriesDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
            "Gestión de Categorías", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        CategoryManagementPanel categoryPanel = new CategoryManagementPanel(() -> {
            loadCategories();
            dialog.dispose();
        });
        
        dialog.add(categoryPanel);
        dialog.setVisible(true);
    }
    
    // Panel interno para gestionar categorías
    private class CategoryManagementPanel extends JPanel {
        private JTextField categoryNameField, categoryDescField;
        private JList<Category> categoryList;
        private DefaultListModel<Category> listModel;
        private Runnable onCloseCallback;
        
        public CategoryManagementPanel(Runnable onCloseCallback) {
            this.onCloseCallback = onCloseCallback;
            initComponents();
            setupLayout();
            setupEventListeners();
            refreshCategoryList();
        }
        
        private void initComponents() {
            categoryNameField = new JTextField(15);
            categoryDescField = new JTextField(20);
            listModel = new DefaultListModel<>();
            categoryList = new JList<>(listModel);
            categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        
        private void setupLayout() {
            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JPanel formPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            
            gbc.gridx = 0; gbc.gridy = 0;
            formPanel.add(new JLabel("Nombre:"), gbc);
            gbc.gridx = 1;
            formPanel.add(categoryNameField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 1;
            formPanel.add(new JLabel("Descripción:"), gbc);
            gbc.gridx = 1;
            formPanel.add(categoryDescField, gbc);
            
            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton addCatButton = new JButton("Agregar");
            JButton closeCatButton = new JButton("Cerrar");
            
            addCatButton.addActionListener(e -> addCategory());
            closeCatButton.addActionListener(e -> onCloseCallback.run());
            
            buttonPanel.add(addCatButton);
            buttonPanel.add(closeCatButton);
            
            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
            formPanel.add(buttonPanel, gbc);
            
            add(formPanel, BorderLayout.NORTH);
            add(new JScrollPane(categoryList), BorderLayout.CENTER);
        }
        
        private void setupEventListeners() {
            // No listeners adicionales necesarios para este panel simple
        }
        
        private void addCategory() {
            if (!categoryNameField.getText().trim().isEmpty()) {
                Category category = new Category(
                    categoryNameField.getText().trim(),
                    categoryDescField.getText().trim()
                );
                
                if (categoryDAO.create(category)) {
                    JOptionPane.showMessageDialog(this, "✅ Categoría agregada exitosamente");
                    refreshCategoryList();
                    categoryNameField.setText("");
                    categoryDescField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error al agregar categoría");
                }
            }
        }
        
        private void refreshCategoryList() {
            listModel.clear();
            List<Category> categories = categoryDAO.readAll();
            for (Category category : categories) {
                listModel.addElement(category);
            }
        }
    }
}