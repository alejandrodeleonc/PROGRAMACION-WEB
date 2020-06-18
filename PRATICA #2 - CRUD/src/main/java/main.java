import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.javalin.apibuilder.ApiBuilder.*;

public class main {
    public static void main(String[] args) {
        Controladora control = Controladora.getInstance();
        ArrayList<Producto> prueba = control.getProductos();
        print("tamano: "+prueba.size());
        Javalin app = Javalin.create(config ->{
            config.addStaticFiles("/publico"); //desde la carpeta de resources
            config.registerPlugin(new RouteOverviewPlugin("/rutas")); //aplicando plugins de las rutas
        }).start(8000);

        app.get("/", ctx -> ctx.result("Hola Mundo en Javalin :-D"));
            app.post("/Carrito", ctx -> {
                //obteniendo la información enviada.
                int id = ctx.formParam("id", Integer.class).get();
                String nombre = ctx.formParam("nombre");
                String descripcion = ctx.formParam("descripcion");
                String auxprecio = ctx.formParam("precio");
                BigDecimal precio = new BigDecimal(auxprecio);
                Producto prod = new Producto(id,nombre,descripcion,precio);
                print(prod.getNombre());
                control.creaProducto(prod);
                ctx.redirect("/Carrito");
            });


            app.get("/Carrito", ctx -> {
                //tomando el parametro utl y validando el tipo.
                List<Producto> lista = Controladora.getInstance().getProductos();
                //
                Map<String, Object> modelo = new HashMap<>();
                modelo.put("titulo", "Listado de productos");
                modelo.put("lista", lista);
                //enviando al sistema de plantilla.
                ctx.render("Publico/Carrito.html", modelo);

            });

            app.delete("/Carrito", ctx -> {
                int id = ctx.formParam("ID", Integer.class).get();
                String nombre = ctx.formParam("Nombre");
                String descripcion = ctx.formParam("Nescripcion");
                String auxprecio = ctx.formParam("Precio");
                BigDecimal precio = new BigDecimal(auxprecio);
                Producto prod = new Producto(id,nombre,descripcion,precio);
                print(prod.getNombre());
                control.borraProducto(prod);

                ctx.render("Publico/Carrito.html", modelo);

            });


    }
        public static void print (String string){

            System.out.println(string);
        }

}





