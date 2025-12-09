package domain;

import java.io.Serializable;

public class DetalleOrden implements Serializable {
    private static final long serialVersionUID = 1L;

    private ItemCatalogo item;
    private int cantidad;

    public DetalleOrden(ItemCatalogo item, int cantidad) {
        this.item = item;
        this.cantidad = cantidad;
    }

    public ItemCatalogo getItem() { return item; }
    public int getCantidad() { return cantidad; }
    public double getSubtotal() { return item.getPrecio() * cantidad; }

    @Override
    public String toString() {
        return item.getNombre() + " x" + cantidad + " = $" + getSubtotal();
    }
}
