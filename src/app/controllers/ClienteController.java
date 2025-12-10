package app.controllers;

import domain.Cliente;
import service.DistrisoftService;
import java.util.List;

public class ClienteController {

    private DistrisoftService service;

    public ClienteController(DistrisoftService service) {
        this.service = service;
    }

    public void agregarCliente(String nombre, String telefono) {
        service.agregarCliente(new Cliente(nombre, telefono));
    }

    public void agregarCliente(String nombre, String telefono, String direccion) {
        service.agregarCliente(new Cliente(nombre, telefono, direccion));
    }

    public List<Cliente> listarClientes() {
        return service.getClientes();
    }

    public void eliminarCliente(int clienteId) {
        service.eliminarCliente(clienteId);
    }
}

