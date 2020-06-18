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
            config.addStaticFiles("/Publico"); //desde la carpeta de resources
            config.registerPlugin(new RouteOverviewPlugin("/rutas")); //aplicando plugins de las rutas
        }).start(8000);
            /* MANEJO DE LA PAGINA PRINCIPAL*/
            app.get("/", ctx ->{ ctx.redirect("/Principal");});

            app.get("/Principal", ctx -> {
                //tomando el parametro utl y validando el tipo.
                List<Producto> lista = Controladora.getInstance().getProductos();
                //
                Map<String, Object> modelo = new HashMap<>();
                modelo.put("titulo", "Listado de productos");
                modelo.put("lista", lista);
                //enviando al sistema de plantilla.
                ctx.render("Publico/principal.html", modelo);

            });

            /* MANEJO DEL LOGIN Y REGISTRAR*/

            app.before("/login", ctx -> {
                print("Entro a verificar el login");
                String usuario = ctx.formParam("user");
                String contrasena = ctx.formParam("password");
                if(!control.verificarUsuario(usuario, contrasena)){
                    ctx.redirect("login.html");
                }
            });
            app.get("/login", ctx -> {
                ctx.redirect("login.html");
            });
            app.post("/login", ctx -> {
                print("Entro a verificar el login");
                String usuario = ctx.formParam("user");
                String contrasena = ctx.formParam("password");
                ctx.sessionAttribute("user",usuario);
                ctx.redirect("/");
            });
            /* REGISTRAR*/
            app.get("/registar", ctx -> {

                ctx.redirect("/nuevousuario.html");
            });

            app.post("/registar", ctx -> {
                String nombre = ctx.formParam("nombre");
                String user = ctx.formParam("usuario");
                String password = ctx.formParam("contrasena");
                Usuario tmp = new Usuario(user,nombre,password);
                control.crearUsuario(tmp);
                print("Se creo usuario");
                print(""+control.getUsuarios().size());
                ctx.redirect("/login.html");
            });


            /* MANEJO DE LA PARTE DE GESTION DE PRODUCTOS*/

            app.before("/Carrito", ctx -> {
                print("Entro a verificar");
                if(ctx.sessionAttribute("user")==null){
                    print("No usuario");
                    ctx.redirect("/login.html");
                }
            });
            app.get("/Carrito", ctx -> {
               List<Producto> lista = Controladora.getInstance().getProductos();
                if(ctx.sessionAttribute("user").equals("admin")) {
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Listado de productos");
                    modelo.put("lista", lista);

                    ctx.render("Publico/Carrito.html", modelo);
                }else{
                    ctx.result("Que permiso del diablo tienes para estar aqui, vete para la pagina principal mejor mmg");
                }
            });
            app.post("/Carrito", ctx -> {

                //obteniendo la información enviada.
                int id = ctx.formParam("id", Integer.class).get();
                String nombre = ctx.formParam("nombre");
                int cantidad = ctx.formParam("descripcion", Integer.class).get();
                String auxprecio = ctx.formParam("precio");
                BigDecimal precio = new BigDecimal(auxprecio);
                Producto prod = new Producto(id,nombre,cantidad,precio);
                print(prod.getNombre());
                control.creaProducto(prod);
                ctx.redirect("/Carrito");
            });



    }
        public static void print (String string){

            System.out.println(string);
        }

}





