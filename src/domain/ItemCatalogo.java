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

    public ItemCatalogo(String nombre, double precio, int stock) {
        this.id = COUNTER.getAndIncrement();
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return id + " - " + nombre + " ($" + precio + ") [" + stock + "]";
    }
}
