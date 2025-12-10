package app.views;

import service.DistrisoftService;
import app.controllers.OrdenController;
import domain.Orden;
import domain.EstadoOrden;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class VerOrdenesPanel extends JPanel {

    public VerOrdenesPanel(DistrisoftService service) {

        OrdenController controller = new OrdenController(service);

        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Fecha", "Estado", "Items"}, 0);
        JTable tabla = new JTable(model);

        // cargar órdenes inicialmente
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        NumberFormat moneda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO"));

        Runnable reload = () -> {
            model.setRowCount(0);
            for (Orden orden : controller.listarOrdenes()) {
                String fechaStr = orden.getFecha() != null ? orden.getFecha().format(dtf) : "";
                // build items summary without currency formatting inside domain
                String resumen = orden.getResumen();
                model.addRow(new Object[]{orden.getId(), fechaStr, orden.getEstado(), resumen});
            }
        };
        reload.run();

        // recargar cuando el panel se muestre (para reflejar órdenes nuevas)
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                reload.run();
            }
        });

        // Botones para cambiar estado de la orden y ver/eliminar
        JButton btnFinalizar = new JButton("Marcar como Entregada");
        JButton btnCancelar = new JButton("Cancelar Orden");
        JButton btnVer = new JButton("Ver Pedido");
        JButton btnEliminar = new JButton("Eliminar Orden");

        btnFinalizar.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una orden.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int ordenId = (int) model.getValueAt(row, 0);
            controller.cambiarEstadoOrden(ordenId, EstadoOrden.ENTREGADA);
            model.setValueAt(EstadoOrden.ENTREGADA, row, 2);
        });

        btnCancelar.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una orden.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int ordenId = (int) model.getValueAt(row, 0);
            controller.cambiarEstadoOrden(ordenId, EstadoOrden.CANCELADA);
            model.setValueAt(EstadoOrden.CANCELADA, row, 2);
        });

            btnVer.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una orden.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int ordenId = (int) model.getValueAt(row, 0);
            Orden orden = controller.getOrdenById(ordenId);
            if (orden == null) return;
            StringBuilder sb = new StringBuilder();
            sb.append("ID: ").append(orden.getId()).append('\n');
            sb.append("Cliente: ").append(orden.getCliente() != null ? orden.getCliente().getNombre() : "(sin cliente)").append('\n');
            String fechaStr = orden.getFecha() != null ? orden.getFecha().format(dtf) : "";
            sb.append("Fecha: ").append(fechaStr).append('\n');
            sb.append("Estado: ").append(orden.getEstado()).append('\n');
            sb.append("\nItems:\n");
            orden.getDetalles().forEach(d -> {
                String linea = String.format(" - %s x%d = %s", d.getItem().getNombre(), d.getCantidad(), moneda.format(d.getSubtotal()));
                sb.append(linea).append('\n');
            });
            sb.append("\nTotal: ").append(moneda.format(orden.getTotal())).append('\n');
            JOptionPane.showMessageDialog(this, sb.toString(), "Detalle de la Orden", JOptionPane.INFORMATION_MESSAGE);
        });

        btnEliminar.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una orden.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            EstadoOrden estado = (EstadoOrden) model.getValueAt(row, 2);
            if (!(estado == EstadoOrden.ENTREGADA || estado == EstadoOrden.CANCELADA)) {
                JOptionPane.showMessageDialog(this, "Sólo se pueden eliminar órdenes que estén ENTREGADA o CANCELADA.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int ordenId = (int) model.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar la orden ID " + ordenId + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.eliminarOrden(ordenId);
                ((DefaultTableModel) tabla.getModel()).removeRow(row);
            }
        });

        JPanel botones = new JPanel();
        botones.add(btnFinalizar);
        botones.add(btnCancelar);
        botones.add(btnVer);
        botones.add(btnEliminar);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }
}
