package app.views;

import javax.swing.*;
import java.awt.*;
import service.DistrisoftService;

public class MenuPrincipalPanel extends JPanel {

    public MenuPrincipalPanel() {

        setLayout(new BorderLayout());
        DistrisoftService service = new DistrisoftService();

        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(1, 4));

        JButton btnClientes = new JButton("Clientes");
        JButton btnCatalogo = new JButton("Catálogo");
        JButton btnCrearOrden = new JButton("Crear Orden");
        JButton btnVerOrdenes = new JButton("Historial");

        JPanel contenido = new JPanel(new CardLayout());

        contenido.add(new ClientesPanel(service), "clientes");
        contenido.add(new CatalogoPanel(service), "catalogo");
        contenido.add(new CrearOrdenPanel(service), "crearOrden");
        contenido.add(new VerOrdenesPanel(service), "ordenes");

        btnClientes.addActionListener(e -> ((CardLayout)contenido.getLayout()).show(contenido, "clientes"));
        btnCatalogo.addActionListener(e -> ((CardLayout)contenido.getLayout()).show(contenido, "catalogo"));
        btnCrearOrden.addActionListener(e -> ((CardLayout)contenido.getLayout()).show(contenido, "crearOrden"));
        btnVerOrdenes.addActionListener(e -> ((CardLayout)contenido.getLayout()).show(contenido, "ordenes"));

        menu.add(btnClientes);
        menu.add(btnCatalogo);
        menu.add(btnCrearOrden);
        menu.add(btnVerOrdenes);

        add(menu, BorderLayout.NORTH);
        add(contenido, BorderLayout.CENTER);
    }
}
