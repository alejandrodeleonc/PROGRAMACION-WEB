package servicios;

import encapsulacion.VentasProductos;

public class VentasServices extends GestionDB<VentasProductos>{
    private static VentasServices instancia;

    private VentasServices() { super(VentasProductos.class); }

    public static VentasServices getInstancia(){
        if(instancia==null){
            instancia = new VentasServices();
        }
        return instancia;
    }

}
