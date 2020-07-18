package encapsulacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "VENTAS")
public class VentasProductos {
    @Id
    private String id;
    private Date fechaCompra;
    private String nombreCliente;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PRODUCTOSPORVENTA")
    private List<ventaprod> listaventaprod;
    private float  total;



    public VentasProductos(String id, Date fechaCompra, String nombreCliente, List<ventaprod> listaProductos, float total) {
        this.id = id;
        this.fechaCompra = fechaCompra;
        this.nombreCliente = nombreCliente;
        this.listaventaprod = listaProductos;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public List<ventaprod> getListaProductos() {
        return listaventaprod;
    }

    public void setListaProductos(ArrayList<ventaprod> listaProductos) {
        this.listaventaprod = listaProductos;
    }
    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
