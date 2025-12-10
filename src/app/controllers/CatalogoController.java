package app.controllers;

import domain.ItemCatalogo;
import service.DistrisoftService;
import java.util.List;

// Controlador ligero que expone operaciones del catálogo desde la capa de servicio
// Funciona como intermediario entre la UI y el servicio `DistrisoftService`.
// Mantiene referencias al servicio y delega llamadas.

public class CatalogoController {

    private DistrisoftService service;

    public CatalogoController(DistrisoftService service) {
        this.service = service;
    }

    public List<ItemCatalogo> obtenerCatalogo() {
        // Devuelve la lista de items consultando el servicio
        return service.getCatalogo();
    }
}
