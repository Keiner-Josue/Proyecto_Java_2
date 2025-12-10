package app.views.componentesUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TablaEstilo extends JTable {

    public TablaEstilo(DefaultTableModel model) {
        super(model);
        // Tabla con estilo consistente: altura de fila y fuentes
        setRowHeight(28);
        setFont(new Font("Arial", Font.PLAIN, 13));
        getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
    }
}

