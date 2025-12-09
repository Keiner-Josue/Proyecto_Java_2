package app.views;

import service.DistrisoftService;
import domain.ItemCatalogo;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class CatalogoPanel extends JPanel {

    public CatalogoPanel(DistrisoftService service) {

        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(new Object[]{
            "Producto", "Precio", "Stock", "Presentación"
        }, 0);

        List<ItemCatalogo> items = new ArrayList<>();
        NumberFormat moneda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO"));
        for (ItemCatalogo item : service.getCatalogo()) {
            items.add(item);
            String presentacion = item.getUnidadCantidad() + "" + item.getUnidad();
            model.addRow(new Object[]{item.getNombre(), moneda.format(item.getPrecio()), item.getStock(), presentacion});
        }

        JTable tabla = new JTable(model);

        // Formulario para agregar nuevos productos
        JPanel form = new JPanel();
        JTextField txtNombre = new JTextField(15);
        JTextField txtPrecio = new JTextField(8);
        JTextField txtStock = new JTextField(5);
        JComboBox<String> comboUnidad = new JComboBox<>(new String[]{"u", "kg", "g", "L", "ml"});
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(Double.valueOf(1.0), Double.valueOf(0.0), null, Double.valueOf(0.1));
        JSpinner spinnerUnidad = new JSpinner(spinnerModel);
        JButton btnAgregar = new JButton("Agregar Producto");

        form.add(new JLabel("Nombre:"));
        form.add(txtNombre);
        form.add(new JLabel("Precio:"));
        form.add(txtPrecio);
        form.add(new JLabel("Stock:"));
        form.add(txtStock);
        form.add(new JLabel("Unidad:"));
        form.add(comboUnidad);
        form.add(new JLabel("Cantidad unidad:"));
        form.add(spinnerUnidad);
        form.add(btnAgregar);

        btnAgregar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String precioStr = txtPrecio.getText().trim();
            String stockStr = txtStock.getText().trim();
            if (nombre.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!nombre.matches("^[\\p{L} ]+$")) {
                JOptionPane.showMessageDialog(this, "El nombre del producto no puede contener números ni caracteres especiales.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                double precio = Double.parseDouble(precioStr);
                if (precio <= 0) {
                    JOptionPane.showMessageDialog(this, "El precio debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int stock = Integer.parseInt(stockStr);
                if (stock < 0) {
                    JOptionPane.showMessageDialog(this, "El stock no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String unidad = (String) comboUnidad.getSelectedItem();
                double unidadCantidad = ((Number) spinnerUnidad.getValue()).doubleValue();
                ItemCatalogo nuevo = new ItemCatalogo(nombre, precio, stock, unidad, unidadCantidad);
                service.agregarItem(nuevo);
                items.add(nuevo);
                model.addRow(new Object[]{nuevo.getNombre(), moneda.format(nuevo.getPrecio()), nuevo.getStock(), unidadCantidad + unidad});
                txtNombre.setText("");
                txtPrecio.setText("");
                txtStock.setText("");
                spinnerUnidad.setValue(Double.valueOf(1.0));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Precio o stock no válidos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Botón para eliminar producto seleccionado
        JButton btnEliminar = new JButton("Eliminar Producto");
        btnEliminar.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            ItemCatalogo seleccionado = items.remove(row);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar producto '" + seleccionado.getNombre() + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                service.eliminarItem(seleccionado.getId());
                ((DefaultTableModel) tabla.getModel()).removeRow(row);
            } else {
                // si no confirma, volver a insertar en la lista en la misma posición
                items.add(row, seleccionado);
            }
        });

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(btnEliminar, BorderLayout.SOUTH);
    }
}
