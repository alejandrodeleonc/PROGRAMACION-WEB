import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class main {
    public static void main(String[] args) {
        Controladora control = Controladora.getInstance();
        ArrayList<Producto> prueba = control.getProductos();
        CarroCompra carroCompra = new CarroCompra(null);
        AtomicBoolean status = new AtomicBoolean(false);



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
                int aux = 0;
                if(carroCompra.getId()!=null){
                    for (Producto p: carroCompra.getListaProductos()) {
                        aux+=p.getCantidad();
                    }
                }

                modelo.put("cantidad",aux);
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
                String id = ctx.req.getSession().getId();
                carroCompra.setId(id);

                print(carroCompra.getId());
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
            app.before("/admprod", ctx -> {
                print("Entro a verificar");
                if(ctx.sessionAttribute("user")==null) {
                    print("No usuario");
                    ctx.redirect("/login.html");
                }
            });

            app.get("/admprod", ctx ->{

                List<Producto> lista = control.getProductos();

                if(ctx.sessionAttribute("user").equals("admin")){
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Lista de productos");
                    modelo.put("lista", lista);
                    ctx.render("Publico/admproductos.html", modelo);
                }else{
                    ctx.result("No tienes permiso para acceder a esta pagina");
                }

            });
            app.get("/nuevoproducto", ctx -> {
                Map<String, Object> modelo = new HashMap<>();
                modelo.put("titulo", "Crear nuevo producto");
                modelo.put("boton", "Crear");
                modelo.put("accion", "/nuevoproducto");
                modelo.put("error", "");
                ctx.render("Publico/editarcrearprod.html",modelo);
            });

            app.post("/nuevoproducto", ctx -> {
                Map<String, Object> modelo = new HashMap<>();
                boolean aux = false;
                int id = ctx.formParam("id", Integer.class).get();
                String nombre = ctx.formParam("nombre");
                int cantidad = ctx.formParam("cantidad", Integer.class).get();
                String auxprecio = ctx.formParam("precio");
                BigDecimal precio = new BigDecimal(auxprecio);
                for (Producto producto: control.getProductos()) {
                    if(producto.getId() == id){
                        aux = true;
                        break;
                    }else{
                        aux = false;
                    }
                }
                if(aux){
                    modelo.put("titulo", "Crear nuevo producto");
                    modelo.put("boton", "Crear");
                    modelo.put("error", "ERROR: YA EXISTE UN PRODUCTO CON ESE ID");
                    ctx.render("Publico/editarcrearprod.html", modelo);

                }else{
                    Producto prod = new Producto(id,nombre,cantidad,precio);
                    control.creaProducto(prod);
                    modelo.put("titulo", "Crear nuevo producto");
                    modelo.put("boton", "Crear");
                    modelo.put("error", "");
                    ctx.render("Publico/editarcrearprod.html",modelo);
                }

            });

            app.get("/editar/:id", ctx-> {
                Map<String, Object> modelo = new HashMap<>();
                Producto producto = control.buscaProductobyid(ctx.pathParam("id", Integer.class).get());
                modelo.put("titulo", "Editar producto");
                modelo.put("boton", "Editar");
                modelo.put("idval", Integer.toString(producto.getId()));
                modelo.put("nombreval", producto.getNombre());
                modelo.put("cantidadval", Integer.toString(producto.getCantidad()));
                modelo.put("precioval", producto.getPrecio().toString());
                modelo.put("accion", "/editar");
                ctx.render("Publico/editarcrearprod.html", modelo);

            });

            app.post("/editar", ctx -> {
                Map<String, Object> modelo = new HashMap<>();

                int id = ctx.formParam("id", Integer.class).get();
                String nombre = ctx.formParam("nombre");
                int cantidad = ctx.formParam("cantidad", Integer.class).get();
                String auxprecio = ctx.formParam("precio");
                BigDecimal precio = new BigDecimal(auxprecio);
                Producto tmp = new Producto(id,nombre,cantidad,precio);

                if(control.editarProducto(tmp) == null){
                    modelo.put("error","Error: Producto no encontrado");
                    modelo.put("accion", "/editar");
                    modelo.put("titulo", "Editar producto");
                    modelo.put("boton", "Editar");
                    ctx.render("Publico/editarcrearprod.html", modelo);
                }else{
                    ctx.redirect("/admprod");
                }

            });

            app.get("/eliminar/:id", ctx-> {
                Map<String, Object> modelo = new HashMap<>();
                Producto producto = control.buscaProductobyid(ctx.pathParam("id", Integer.class).get());
                control.borraProducto(producto);
                ctx.redirect("/admprod");

            });

            /*CONTROLANDO LOS ITEMS ANADIDOS AL CARRITO DE UN USUARIO PARTICULAR*/
            app.get("/agregarcar", ctx -> {
                BigDecimal p = new BigDecimal(581.5);
                int cantidad = ctx.formParam("cantidad", Integer.class).get();
                Producto m = new Producto(5,"Juan",cantidad,p);
                carroCompra.setListaProductos(m);
                ctx.redirect("/");
            });



    }
        public static void print (String string){

            System.out.println(string);
        }

}





