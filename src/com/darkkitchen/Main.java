package com.darkkitchen;

import com.darkkitchen.dao.DatabaseConnection;
import com.darkkitchen.ui.MainFrame;
import javax.swing.*;

/**
 * Clase principal para inicializar la aplicación Dark Kitchen
 */
public class Main {
    
    public static void main(String[] args) {
        // Configurar apariencia básica
        System.out.println("🎨 Configurando interfaz...");
        
        // Ejecutar en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Mostrar splash screen mientras se carga la aplicación
                showSplashScreen();
                
                // Crear y mostrar la ventana principal
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                
                System.out.println("🥘 Dark Kitchen - Aplicación iniciada exitosamente");
                
            } catch (Exception e) {
                System.err.println("❌ Error al iniciar la aplicación: " + e.getMessage());
                e.printStackTrace();
                
                // Mostrar error al usuario
                JOptionPane.showMessageDialog(null,
                    "Error al iniciar la aplicación:\n" + e.getMessage(),
                    "Error de Inicio",
                    JOptionPane.ERROR_MESSAGE);
                
                // Cerrar conexión y salir
                DatabaseConnection.closeConnection();
                System.exit(1);
            }
        });
    }
    
    /**
     * Muestra una ventana de splash simple durante el inicio
     */
    private static void showSplashScreen() {
        JWindow splash = new JWindow();
        
        // Crear panel del splash
        JPanel splashPanel = new JPanel();
        splashPanel.setLayout(new BoxLayout(splashPanel, BoxLayout.Y_AXIS));
        splashPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(20, 40, 20, 40)
        ));
        splashPanel.setBackground(new java.awt.Color(255, 255, 255));
        
        // Título
        JLabel titleLabel = new JLabel("🥘 Dark Kitchen");
        titleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
        titleLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Subtítulo
        JLabel subtitleLabel = new JLabel("Sistema de Gestión");
        subtitleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        subtitleLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setForeground(new java.awt.Color(128, 128, 128));
        
        // Mensaje de carga
        JLabel loadingLabel = new JLabel("Cargando...");
        loadingLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        loadingLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Barra de progreso indeterminada
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        
        // Agregar componentes al panel
        splashPanel.add(Box.createVerticalStrut(10));
        splashPanel.add(titleLabel);
        splashPanel.add(Box.createVerticalStrut(10));
        splashPanel.add(subtitleLabel);
        splashPanel.add(Box.createVerticalStrut(20));
        splashPanel.add(loadingLabel);
        splashPanel.add(Box.createVerticalStrut(10));
        splashPanel.add(progressBar);
        splashPanel.add(Box.createVerticalStrut(10));
        
        // Configurar ventana splash
        splash.setContentPane(splashPanel);
        splash.setSize(300, 200);
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);
        
        // Simular tiempo de carga
        try {
            Thread.sleep(2000); // 2 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Cerrar splash
        splash.setVisible(false);
        splash.dispose();
    }
}