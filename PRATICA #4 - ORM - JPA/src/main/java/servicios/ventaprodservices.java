package servicios;
import encapsulacion.ventaprod;


public class ventaprodservices extends GestionDB<ventaprod> {


    private static servicios.ventaprodservices instancia;

    private ventaprodservices() { super(ventaprod.class); }

    public static servicios.ventaprodservices getInstancia(){
        if(instancia==null){
            instancia = new servicios.ventaprodservices();
        }
        return instancia;
    }


}
