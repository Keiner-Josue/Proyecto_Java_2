package domain;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class ItemCatalogo implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final AtomicInteger COUNTER = new AtomicInteger(1);

    private final int id;
    private String nombre;
    private double precio;
    private int stock;
    private String unidad; // e.g., "kg", "g", "L", "ml", "u"
    private double unidadCantidad; // e.g., 1.0 (1 kg), 500.0 (500 g), 0.5 (0.5 L)

    public ItemCatalogo(String nombre, double precio, int stock) {
        this.id = COUNTER.getAndIncrement();
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.unidad = "u";
        this.unidadCantidad = 1.0;
    }

    public ItemCatalogo(String nombre, double precio, int stock, String unidad, double unidadCantidad) {
        this.id = COUNTER.getAndIncrement();
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.unidad = unidad == null ? "u" : unidad;
        this.unidadCantidad = unidadCantidad <= 0 ? 1.0 : unidadCantidad;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }
    public double getUnidadCantidad() { return unidadCantidad; }
    public void setUnidadCantidad(double unidadCantidad) { this.unidadCantidad = unidadCantidad; }

    @Override
    public String toString() {
        // Representación compacta usada en combos y debugging
        return id + " - " + nombre + " ($" + precio + ") [" + stock + "] (" + unidadCantidad + unidad + ")";
    }
}
