package app.controllers;

import domain.Cliente;
import service.DistrisoftService;
import java.util.List;

// Controlador para operaciones CRUD de clientes.
// Valida/transforma argumentos mínimos y delega en `DistrisoftService`.

public class ClienteController {

    private DistrisoftService service;

    public ClienteController(DistrisoftService service) {
        this.service = service;
    }

    public void agregarCliente(String nombre, String telefono) {
        // Crea un objeto Cliente y lo delega al servicio para persistencia
        service.agregarCliente(new Cliente(nombre, telefono));
    }

    public void agregarCliente(String nombre, String telefono, String direccion) {
        // Sobrecarga que incluye dirección
        service.agregarCliente(new Cliente(nombre, telefono, direccion));
    }

    public List<Cliente> listarClientes() {
        // Retorna la lista actual de clientes
        return service.getClientes();
    }

    public void eliminarCliente(int clienteId) {
        // Elimina por id delegando en el servicio
        service.eliminarCliente(clienteId);
    }
}

