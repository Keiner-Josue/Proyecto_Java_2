package app.controllers;

import domain.ItemCatalogo;
import service.DistrisoftService;
import java.util.List;

public class CatalogoController {

    private DistrisoftService service;

    public CatalogoController(DistrisoftService service) {
        this.service = service;
    }

    public List<ItemCatalogo> obtenerCatalogo() {
        return service.getCatalogo();
    }
}
