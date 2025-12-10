package app.views.componentesUI;

import javax.swing.*;
import java.awt.*;

public class CampoTexto extends JTextField {

    public CampoTexto(int size) {
        super(size);
        // Campo de texto con fuente estandarizada para la UI
        setFont(new Font("Arial", Font.PLAIN, 14));
    }
}
