package app;

import app.views.MenuPrincipalPanel;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Ejecuta la creación de la interfaz en el hilo de Swing (recomendado)
        SwingUtilities.invokeLater(() -> {

            // Ventana principal de la aplicación
            JFrame frame = new JFrame("DISTRISOFT - Plataforma de Proveedores");
            // Cerrar la aplicación al cerrar la ventana
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // Tamaño inicial de la ventana
            frame.setSize(1100, 700);
            // Centrar la ventana en pantalla
            frame.setLocationRelativeTo(null);

            // Poner el panel principal (que contiene la navegación y tarjetas)
            frame.setContentPane(new MenuPrincipalPanel());
            // Mostrar la ventana
            frame.setVisible(true);
        });
    }
}
