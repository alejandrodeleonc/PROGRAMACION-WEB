import java.math.BigDecimal;
import java.util.ArrayList;

public class Controladora{
    private static Controladora controladora = null;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Producto> productos;
    private ArrayList<VentasProductos> ventas;

    public Controladora() {
        usuarios = new ArrayList<Usuario>();
        Usuario ad = new Usuario("admin","Administrator","admin");
        usuarios.add(ad);
        productos = new ArrayList<Producto>();
        BigDecimal po = new BigDecimal(258.50);
        Producto p = new Producto(1,"Prueba","CAnsado",po);
        productos.add(p);
        ventas = new ArrayList<VentasProductos>();

    }
    public static Controladora getInstance() {
        if (controladora == null) {
            controladora = new Controladora();
        }
        return controladora;
    }

    public static Controladora getControladora() {
        return controladora;
    }

    public static void setControladora(Controladora controladora) {
        Controladora.controladora = controladora;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public ArrayList<VentasProductos> getVentas() {
        return ventas;
    }

    public void setVentas(ArrayList<VentasProductos> ventas) {
        this.ventas = ventas;
    }

    public boolean crearUsuario(String nombre, String usuario, String password){
        boolean aux = false;
        int a = this.usuarios.size();
        Usuario us = new Usuario(usuario,nombre,password);
        this.usuarios.add(us);
        if(this.usuarios.size()>a){
            aux = true;
        }
        return aux;
    }

    public void creaProducto(Producto producto){
       this.productos.add(producto);
    }
    public void borraProducto(Producto producto){
        this.productos.del(producto);

    }
}

