package domain;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final AtomicInteger COUNTER = new AtomicInteger(1);

    private final int id;
    private String nombre;
    private String telefono;
    private String direccion;

    // Constructor principal: asigna id secuencial y campos
    public Cliente(String nombre, String telefono, String direccion) {
        this.id = COUNTER.getAndIncrement();
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Conveniencia sin dirección (vacía)
    public Cliente(String nombre, String telefono) {
        this(nombre, telefono, "");
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { 
        this.nombre = nombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    @Override
    public String toString() {
        // Muestra identificador y nombre, útil en combos y logs
        return id + " - " + nombre;
    }
}
