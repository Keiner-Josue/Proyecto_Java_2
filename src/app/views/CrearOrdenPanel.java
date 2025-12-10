package app.views;

import service.DistrisoftService;
import app.controllers.OrdenController;
import app.controllers.ClienteController;
import domain.*;

import javax.swing.*;
import javax.swing.DefaultComboBoxModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

public class CrearOrdenPanel extends JPanel {

    public CrearOrdenPanel(DistrisoftService service) {

        OrdenController controller = new OrdenController(service);

        setLayout(new BorderLayout());

        DefaultComboBoxModel<ItemCatalogo> comboModel = new DefaultComboBoxModel<>();
        for (ItemCatalogo item : controller.getCatalogo()) {
            comboModel.addElement(item);
        }
        JComboBox<ItemCatalogo> comboItems = new JComboBox<>(comboModel);
        comboItems.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ItemCatalogo) {
                    ItemCatalogo it = (ItemCatalogo) value;
                    setText(it.getNombre() + " ($" + it.getPrecio() + ")");
                }
                return this;
            }
        });

        // cuando el panel se muestre, recargar catálogo (para reflejar productos agregados en otra tarjeta)
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                comboModel.removeAllElements();
                for (ItemCatalogo item : controller.getCatalogo()) comboModel.addElement(item);
            }
        });

        // Modelo y combo para clientes
        ClienteController clienteController = new ClienteController(service);
        DefaultComboBoxModel<Cliente> clienteModel = new DefaultComboBoxModel<>();
        for (Cliente c : clienteController.listarClientes()) clienteModel.addElement(c);
        JComboBox<Cliente> comboClientes = new JComboBox<>(clienteModel);
        comboClientes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cliente) {
                    Cliente cl = (Cliente) value;
                    setText(cl.getNombre() + " (" + cl.getTelefono() + ")");
                }
                return this;
            }
        });

        // recargar clientes cuando el panel se muestre
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                clienteModel.removeAllElements();
                for (Cliente c : clienteController.listarClientes()) clienteModel.addElement(c);
            }
        });

        JTextField txtCantidad = new JTextField(5);
        JButton btnAgregar = new JButton("Agregar al pedido");

        // Lista de detalles agregados (modelo y vista) para permitir eliminar items antes de guardar
        DefaultListModel<DetalleOrden> detalleModel = new DefaultListModel<>();
        JList<DetalleOrden> listaDetalles = new JList<>(detalleModel);
        listaDetalles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton btnGuardar = new JButton("Guardar Orden");
        JButton btnNuevoCliente = new JButton("Nuevo Cliente");

        List<DetalleOrden> detalles = new ArrayList<>();

        btnAgregar.addActionListener(e -> {
            ItemCatalogo item = (ItemCatalogo) comboItems.getSelectedItem();
            if (item == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un producto.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String cantidadStr = txtCantidad.getText().trim();
            if (cantidadStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese la cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int cantidad;
            try {
                cantidad = Integer.parseInt(cantidadStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cantidad no válida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cantidad > item.getStock()) {
                JOptionPane.showMessageDialog(this, "La cantidad no puede ser mayor al stock disponible (" + item.getStock() + ").", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DetalleOrden detalle = new DetalleOrden(item, cantidad);
            detalles.add(detalle);
            detalleModel.addElement(detalle);
            txtCantidad.setText("");
        });

        JButton btnEliminarDetalle = new JButton("Eliminar seleccionado");
        btnEliminarDetalle.addActionListener(e -> {
            int sel = listaDetalles.getSelectedIndex();
            if (sel < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un item para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            DetalleOrden d = detalleModel.getElementAt(sel);
            int confirm = JOptionPane.showConfirmDialog(this, "Eliminar '" + d.getItem().getNombre() + " x " + d.getCantidad() + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                detalleModel.remove(sel);
                detalles.remove(sel);
            }
        });

        btnGuardar.addActionListener(e -> {
            if (detalles.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Agregue al menos un producto a la orden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Cliente clienteSeleccionado = (Cliente) comboClientes.getSelectedItem();
            if (clienteSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Seleccione o cree un cliente para la orden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            controller.crearOrden(clienteSeleccionado, detalles);
            JOptionPane.showMessageDialog(this, "Orden registrada correctamente");
            // limpiar lista de detalles
            detalleModel.clear();
            detalles.clear();
        });

        btnNuevoCliente.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog(this, "Nombre del cliente:");
            if (nombre == null) return; // cancel
            nombre = nombre.trim();
            if (nombre.isEmpty() || !nombre.matches("^[\\p{L} ]+$")) {
                JOptionPane.showMessageDialog(this, "Nombre inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String telefono = JOptionPane.showInputDialog(this, "Teléfono (máx 10 dígitos):");
            if (telefono == null) return;
            telefono = telefono.trim();
            if (!telefono.matches("^\\d{1,10}$")) {
                JOptionPane.showMessageDialog(this, "Teléfono inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String direccion = JOptionPane.showInputDialog(this, "Dirección (opcional):");
            if (direccion == null) direccion = "";
            clienteController.agregarCliente(nombre, telefono, direccion.trim());
            clienteModel.removeAllElements();
            for (Cliente c : clienteController.listarClientes()) clienteModel.addElement(c);
            // seleccionar el último agregado
            if (clienteModel.getSize() > 0) comboClientes.setSelectedIndex(clienteModel.getSize() - 1);
        });

        JPanel arriba = new JPanel();
        arriba.add(new JLabel("Cliente:"));
        arriba.add(comboClientes);
        arriba.add(btnNuevoCliente);
        arriba.add(new JLabel("Producto:"));
        arriba.add(comboItems);
        arriba.add(new JLabel("Cantidad:"));
        arriba.add(txtCantidad);
        arriba.add(btnAgregar);

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(new JScrollPane(listaDetalles), BorderLayout.CENTER);
        JPanel southButtons = new JPanel();
        southButtons.add(btnEliminarDetalle);
        southButtons.add(btnGuardar);
        centro.add(southButtons, BorderLayout.SOUTH);

        add(arriba, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
    }
}

