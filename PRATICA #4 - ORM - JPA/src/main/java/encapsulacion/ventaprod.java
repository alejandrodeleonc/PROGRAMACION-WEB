package encapsulacion;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCTOSVENDIDOS")
public class ventaprod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JoinColumn(name = "id_prod")
    @OneToOne
    private Producto producto;
    private int cantidad;

    public ventaprod(){}
    public ventaprod(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
