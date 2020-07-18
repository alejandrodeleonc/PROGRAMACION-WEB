package encapsulacion;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
@Entity
public class Producto {
    @Id
    private int id_prod;
    private String nombre;
    private int cantidad;
    private BigDecimal precio;

    public Producto(int id, String nombre, int cantidad, BigDecimal precio) {
        this.id_prod = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getId() {
        return id_prod;
    }

    public void setId(int id) {
        this.id_prod = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    public void mezclar(Producto p){
        this.id_prod = p.getId();
        this.nombre = p.getNombre();
        this.cantidad = p.getCantidad();
        this.precio = p.getPrecio();
    }
}