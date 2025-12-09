package app.controllers;

import domain.Orden;
import domain.ItemCatalogo;
import domain.Cliente;
import domain.DetalleOrden;
import domain.EstadoOrden;
import service.DistrisoftService;
import java.util.List;

public class OrdenController {

    private DistrisoftService service;

    public OrdenController(DistrisoftService service) {
        this.service = service;
    }

    public void crearOrden(Orden orden) {
        service.crearOrden(orden);
    }

    public Orden crearOrden(Cliente cliente, List<DetalleOrden> detalles) {
        return service.crearOrden(cliente, detalles);
    }

    public List<Orden> listarOrdenes() {
        return service.getOrdenes();
    }

    public List<ItemCatalogo> getCatalogo() {
        return service.getCatalogo();
    }

    public void cambiarEstadoOrden(int ordenId, EstadoOrden estado) {
        service.cambiarEstadoOrden(ordenId, estado);
    }

    public void eliminarOrden(int ordenId) {
        service.eliminarOrden(ordenId);
    }

    public Orden getOrdenById(int ordenId) {
        return service.getOrdenes().stream().filter(o -> o.getId() == ordenId).findFirst().orElse(null);
    }
}
