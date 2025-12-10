package app.views;

import app.controllers.ClienteController;
import service.DistrisoftService;
import domain.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class ClientesPanel extends JPanel {

    public ClientesPanel(DistrisoftService service) {
        // Panel para gestionar clientes: formulario + tabla de resultados
        ClienteController controller = new ClienteController(service);

        setLayout(new BorderLayout());

        // Campos del formulario de nuevo cliente
        JTextField txtNombre = new JTextField(15);
        JTextField txtTelefono = new JTextField(15);
        JTextField txtDireccion = new JTextField(20);
        JButton btnAgregar = new JButton("Agregar Cliente");

        JPanel form = new JPanel();
        form.add(new JLabel("Nombre:"));
        form.add(txtNombre);
        form.add(new JLabel("Teléfono:"));
        form.add(txtTelefono);
        form.add(new JLabel("Dirección:"));
        form.add(txtDireccion);
        form.add(btnAgregar);

        // Tabla para mostrar clientes
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre", "Teléfono", "Dirección"}, 0);
        JTable tabla = new JTable(model);

        // Cargar clientes iniciales desde el servicio
        List<Cliente> clientes = new ArrayList<>(controller.listarClientes());
        for (Cliente c : clientes) model.addRow(new Object[]{c.getNombre(), c.getTelefono(), c.getDireccion()});

        JScrollPane scroll = new JScrollPane(tabla);

        // Acción del botón Agregar: validaciones mínimas y delegar creación
        btnAgregar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String telefono = txtTelefono.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!nombre.matches("^[\\p{L} ]+$")) {
                JOptionPane.showMessageDialog(this, "El nombre no puede contener números ni caracteres especiales.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String direccion = txtDireccion.getText().trim();
            if (telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El teléfono es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!telefono.matches("^\\d{1,10}$")) {
                JOptionPane.showMessageDialog(this, "El teléfono debe contener solo dígitos y como máximo 10 números.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Delegar creación y actualizar vista
            controller.agregarCliente(nombre, telefono, direccion);
            clientes.clear();
            clientes.addAll(controller.listarClientes());
            model.setRowCount(0);
            for (Cliente c : clientes) model.addRow(new Object[]{c.getNombre(), c.getTelefono(), c.getDireccion()});

            txtNombre.setText("");
            txtTelefono.setText("");
        });

        // botón para eliminar cliente seleccionado
        JButton btnEliminar = new JButton("Eliminar Cliente");
        btnEliminar.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Cliente seleccionado = clientes.get(row);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar cliente '" + seleccionado.getNombre() + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.eliminarCliente(seleccionado.getId());
                clientes.remove(row);
                ((DefaultTableModel) tabla.getModel()).removeRow(row);
            }
        });

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(scroll, BorderLayout.CENTER);
        bottom.add(btnEliminar, BorderLayout.SOUTH);

        add(form, BorderLayout.NORTH);
        add(bottom, BorderLayout.CENTER);
    }
}
