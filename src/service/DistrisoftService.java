package service;

import domain.Cliente;
import domain.ItemCatalogo;
import domain.Orden;
import domain.DetalleOrden;
import domain.EstadoOrden;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DistrisoftService implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Cliente> clientes;
    private List<ItemCatalogo> catalogo;
    private List<Orden> ordenes;

    private static final String FILE_CLIENTES = "clientes.dat";
    private static final String FILE_CATALOGO = "catalogo.dat";
    private static final String FILE_ORDENES = "ordenes.dat";

    public DistrisoftService() {
        clientes = new ArrayList<>();
        catalogo = new ArrayList<>();
        ordenes = new ArrayList<>();
        loadAll();
    }

    // CLIENTES
    public void agregarCliente(Cliente c) {
        clientes.add(c);
        saveClientes();
    }
    public List<Cliente> getClientes() { return clientes; }

    public void eliminarCliente(int clienteId) {
        clientes.removeIf(c -> c.getId() == clienteId);
        saveClientes();
    }

    // CATALOGO
    public void agregarItem(ItemCatalogo item) {
        catalogo.add(item);
        saveCatalogo();
    }
    public List<ItemCatalogo> getCatalogo() { return catalogo; }

    public void eliminarItem(int itemId) {
        catalogo.removeIf(i -> i.getId() == itemId);
        saveCatalogo();
    }

    public Optional<ItemCatalogo> encontrarItemPorId(int id) {
        return catalogo.stream().filter(i -> i.getId() == id).findFirst();
    }

    public ItemCatalogo buscarItem(String nombre) {
        return catalogo.stream().filter(i -> i.getNombre().equalsIgnoreCase(nombre)).findFirst().orElse(null);
    }

    // ORDENES
    public void agregarOrden(Orden o) {
        for (DetalleOrden d : o.getDetalles()) {
            ItemCatalogo item = d.getItem();
            item.setStock(item.getStock() - d.getCantidad());
        }
        ordenes.add(o);
        saveOrdenes();
        saveCatalogo();
    }
    public List<Orden> getOrdenes() { return ordenes; }

    public void cambiarEstadoOrden(int ordenId, EstadoOrden estado) {
        ordenes.stream()
                .filter(o -> o.getId() == ordenId)
                .findFirst()
                .ifPresent(o -> {
                    o.setEstado(estado);
                    saveOrdenes();
                });
    }

    public void eliminarOrden(int ordenId) {
        ordenes.removeIf(o -> o.getId() == ordenId);
        saveOrdenes();
    }

    // Persistencia básica
    @SuppressWarnings("unchecked")
    private void loadAll() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_CLIENTES))) {
            clientes = (List<Cliente>) ois.readObject();
        } catch (Exception e) {}
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_CATALOGO))) {
            catalogo = (List<ItemCatalogo>) ois.readObject();
        } catch (Exception e) {}
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_ORDENES))) {
            ordenes = (List<Orden>) ois.readObject();
        } catch (Exception e) {}
    }

    private void saveClientes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_CLIENTES))) {
            oos.writeObject(clientes);
        } catch (IOException e) { e.printStackTrace(); }
    }
    private void saveCatalogo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_CATALOGO))) {
            oos.writeObject(catalogo);
        } catch (IOException e) { e.printStackTrace(); }
    }
    private void saveOrdenes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_ORDENES))) {
            oos.writeObject(ordenes);
        } catch (IOException e) { e.printStackTrace(); }
    }

    // utilidad para crear orden
    public Orden crearOrden(Cliente cliente, List<DetalleOrden> detalles) {
        Orden o = new Orden(cliente);
        for (DetalleOrden d : detalles) o.addDetalle(d);
        agregarOrden(o);
        return o;
    }

    // convenience overload to accept an Orden directly
    public Orden crearOrden(Orden orden) {
        agregarOrden(orden);
        return orden;
    }
}
