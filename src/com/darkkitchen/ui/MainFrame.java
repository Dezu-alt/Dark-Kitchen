package com.darkkitchen.ui;

import com.darkkitchen.dao.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Ventana principal de la aplicación Dark Kitchen
 */
public class MainFrame extends JFrame {
    
    private JTabbedPane tabbedPane;
    private CustomerPanel customerPanel;
    private DishPanel dishPanel;
    private JLabel statusLabel;
    
    public MainFrame() {
        initializeComponents();
        setupFrame();
        setupMenuBar();
        setupStatusBar();
        
        // Verificar conexión a la base de datos al iniciar
        checkDatabaseConnection();
    }
    
    private void initializeComponents() {
        tabbedPane = new JTabbedPane();
        customerPanel = new CustomerPanel();
        dishPanel = new DishPanel();
        statusLabel = new JLabel("🔌 Verificando conexión...");
        
        // Agregar pestañas
        tabbedPane.addTab("👥 Clientes", customerPanel);
        tabbedPane.addTab("🍽️ Platillos", dishPanel);
        
        // Configurar ícono de pestaña con mejor estilo
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }
    
    private void setupFrame() {
        setTitle("🥘 Dark Kitchen - Sistema de Gestión");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));
        
        // Configurar layout
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        
        // Manejar cierre de ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });
        
        // Configuración básica
        System.out.println("🎨 Configurando interfaz gráfica...");
    }
    
    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menú Archivo
        JMenu fileMenu = new JMenu("📁 Archivo");
        fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JMenuItem refreshItem = new JMenuItem("🔄 Actualizar Datos");
        refreshItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
        refreshItem.addActionListener(e -> refreshAllData());
        
        JMenuItem exitItem = new JMenuItem("🚪 Salir");
        exitItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
        exitItem.addActionListener(e -> exitApplication());
        
        fileMenu.add(refreshItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        // Menú Base de Datos
        JMenu dbMenu = new JMenu("🗄️ Base de Datos");
        dbMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JMenuItem testConnectionItem = new JMenuItem("🔌 Probar Conexión");
        testConnectionItem.addActionListener(e -> checkDatabaseConnection());
        
        dbMenu.add(testConnectionItem);
        
        // Menú Ayuda
        JMenu helpMenu = new JMenu("❓ Ayuda");
        helpMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JMenuItem aboutItem = new JMenuItem("ℹ️ Acerca de");
        aboutItem.addActionListener(e -> showAboutDialog());
        
        helpMenu.add(aboutItem);
        
        // Agregar menús a la barra
        menuBar.add(fileMenu);
        menuBar.add(dbMenu);
        menuBar.add(Box.createHorizontalGlue()); // Empuja "Ayuda" hacia la derecha
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    private void setupStatusBar() {
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusPanel.setPreferredSize(new Dimension(getWidth(), 25));
        
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusPanel.add(statusLabel);
        
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private void checkDatabaseConnection() {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("🔌 Verificando conexión...");
            statusLabel.setForeground(Color.ORANGE);
        });
        
        // Ejecutar en hilo separado para no bloquear la UI
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return DatabaseConnection.testConnection();
            }
            
            @Override
            protected void done() {
                try {
                    boolean connected = get();
                    if (connected) {
                        statusLabel.setText("✅ Conectado a la base de datos");
                        statusLabel.setForeground(new Color(0, 128, 0));
                    } else {
                        statusLabel.setText("❌ Error de conexión a la base de datos");
                        statusLabel.setForeground(Color.RED);
                        showConnectionErrorDialog();
                    }
                } catch (Exception e) {
                    statusLabel.setText("❌ Error de conexión: " + e.getMessage());
                    statusLabel.setForeground(Color.RED);
                    showConnectionErrorDialog();
                }
            }
        };
        worker.execute();
    }
    
    private void showConnectionErrorDialog() {
        String message = """
            No se pudo conectar a la base de datos.
            
            Verifica que:
            • MySQL esté ejecutándose
            • La base de datos 'dark_kitchen' exista
            • Las credenciales en DatabaseConnection.java sean correctas
            • El conector MySQL esté en la carpeta lib/
            """;
        
        JOptionPane.showMessageDialog(
            this,
            message,
            "Error de Conexión",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    private void refreshAllData() {
        SwingUtilities.invokeLater(() -> {
            customerPanel.refreshData();
            dishPanel.refreshData();
            statusLabel.setText("🔄 Datos actualizados");
        });
        
        // Limpiar el mensaje después de 3 segundos
        Timer timer = new Timer(3000, e -> statusLabel.setText("✅ Listo"));
        timer.setRepeats(false);
        timer.start();
    }
    
    private void showAboutDialog() {
        String message = """
            🥘 Dark Kitchen - Sistema de Gestión
            
            Versión: 1.0
            Desarrollado para: Universidad TecMilenio
            
            Sistema CRUD para gestión de:
            • Clientes
            • Platillos y Categorías
            
            Tecnologías utilizadas:
            • Java Swing
            • MySQL
            • JDBC
            """;
        
        JOptionPane.showMessageDialog(
            this,
            message,
            "Acerca de Dark Kitchen",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void exitApplication() {
        int option = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro de que quieres salir de la aplicación?",
            "Confirmar Salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (option == JOptionPane.YES_OPTION) {
            // Cerrar conexión a la base de datos
            DatabaseConnection.closeConnection();
            
            // Salir de la aplicación
            System.exit(0);
        }
    }
}