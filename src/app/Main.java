package app;

import app.views.MenuPrincipalPanel;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("DISTRISOFT - Plataforma de Proveedores");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 700);
            frame.setLocationRelativeTo(null);

            frame.setContentPane(new MenuPrincipalPanel());
            frame.setVisible(true);
        });
    }
}
