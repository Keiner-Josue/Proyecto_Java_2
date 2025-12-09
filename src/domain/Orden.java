package domain;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Orden implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final AtomicInteger COUNTER = new AtomicInteger(1);

    private final int id;
    private Cliente cliente;
    private List<DetalleOrden> detalles;
    private EstadoOrden estado;
    private LocalDateTime fecha;

    public Orden(Cliente cliente) {
        this.id = COUNTER.getAndIncrement();
        this.cliente = cliente;
        this.detalles = new ArrayList<>();
        this.estado = EstadoOrden.PENDIENTE;
        this.fecha = LocalDateTime.now();
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<DetalleOrden> getDetalles() { return detalles; }
    public EstadoOrden getEstado() { return estado; }
    public void setEstado(EstadoOrden estado) { this.estado = estado; }
    public LocalDateTime getFecha() { return fecha; }

    public void addDetalle(DetalleOrden d) {
        this.detalles.add(d);
    }

    public double getTotal() {
        return detalles.stream().mapToDouble(DetalleOrden::getSubtotal).sum();
    }

    @Override
    public String toString() {
        return "Orden " + id + " - " + cliente.getNombre() + " - " + estado + " - $" + getTotal();
    }
}
