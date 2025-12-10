package app.views;

import javax.swing.*;
import java.awt.*;
import service.DistrisoftService;

public class MenuPrincipalPanel extends JPanel {

    public MenuPrincipalPanel() {
        // Panel principal que contiene el menú superior y el área de contenido (tarjetas)
        setLayout(new BorderLayout());
        // Instancia compartida del servicio que se pasa a los subpaneles
        DistrisoftService service = new DistrisoftService();

        // Barra de botones (navegación entre tarjetas)
        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(1, 4));

        JButton btnClientes = new JButton("Clientes");
        JButton btnCatalogo = new JButton("Catálogo");
        JButton btnCrearOrden = new JButton("Crear Orden");
        JButton btnVerOrdenes = new JButton("Historial");

        // Área de contenido con CardLayout para cambiar vistas
        JPanel contenido = new JPanel(new CardLayout());

        // Añadir las vistas (cada una recibe el servicio)
        contenido.add(new ClientesPanel(service), "clientes");
        contenido.add(new CatalogoPanel(service), "catalogo");
        contenido.add(new CrearOrdenPanel(service), "crearOrden");
        contenido.add(new VerOrdenesPanel(service), "ordenes");

        // Listeners que muestran la tarjeta correspondiente
        btnClientes.addActionListener(e -> ((CardLayout)contenido.getLayout()).show(contenido, "clientes"));
        btnCatalogo.addActionListener(e -> ((CardLayout)contenido.getLayout()).show(contenido, "catalogo"));
        btnCrearOrden.addActionListener(e -> ((CardLayout)contenido.getLayout()).show(contenido, "crearOrden"));
        btnVerOrdenes.addActionListener(e -> ((CardLayout)contenido.getLayout()).show(contenido, "ordenes"));

        // Añadir botones al menú y componer el layout
        menu.add(btnClientes);
        menu.add(btnCatalogo);
        menu.add(btnCrearOrden);
        menu.add(btnVerOrdenes);

        add(menu, BorderLayout.NORTH);
        add(contenido, BorderLayout.CENTER);
    }
}
