package app.controllers;

import domain.Orden;
import domain.ItemCatalogo;
import domain.Cliente;
import domain.DetalleOrden;
import domain.EstadoOrden;
import service.DistrisoftService;
import java.util.List;

// Controlador que agrupa operaciones sobre órdenes y acceso al catálogo.
// Se encarga de delegar la lógica de negocio en `DistrisoftService`.

public class OrdenController {

    private DistrisoftService service;

    public OrdenController(DistrisoftService service) {
        this.service = service;
    }

    public void crearOrden(Orden orden) {
        // Recibe una entidad Orden ya construida y la persiste
        service.crearOrden(orden);
    }

    public Orden crearOrden(Cliente cliente, List<DetalleOrden> detalles) {
        // Crea y persiste una orden a partir de cliente y detalles
        return service.crearOrden(cliente, detalles);
    }

    public List<Orden> listarOrdenes() {
        // Retorna todas las órdenes registradas
        return service.getOrdenes();
    }

    public List<ItemCatalogo> getCatalogo() {
        // Obtiene el catálogo de productos para la UI
        return service.getCatalogo();
    }

    public void cambiarEstadoOrden(int ordenId, EstadoOrden estado) {
        // Cambia el estado de la orden identificada por id
        service.cambiarEstadoOrden(ordenId, estado);
    }

    public void eliminarOrden(int ordenId) {
        // Elimina la orden por id
        service.eliminarOrden(ordenId);
    }

    public Orden getOrdenById(int ordenId) {
        // Busca en memoria la orden por id y la devuelve (o null si no existe)
        return service.getOrdenes().stream().filter(o -> o.getId() == ordenId).findFirst().orElse(null);
    }
}
