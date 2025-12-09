package app.views.componentesUI;

import javax.swing.*;
import java.awt.*;

public class BotonPrimario extends JButton {

    public BotonPrimario(String texto) {
        super(texto);
        setBackground(new Color(0, 120, 215));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setFont(new Font("Arial", Font.BOLD, 14));
    }
}
