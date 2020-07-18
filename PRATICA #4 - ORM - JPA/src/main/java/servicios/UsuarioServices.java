package servicios;
import encapsulacion.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UsuarioServices extends GestionDB<Usuario>{
    private static UsuarioServices instancia;

    private UsuarioServices() { super(Usuario.class); }

    public static UsuarioServices getInstancia(){
        if(instancia==null){
            instancia = new UsuarioServices();
        }
        return instancia;
    }

    public List<Usuario> findAllByNombre(String nombre){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select U from Usuario U where U.nombre like :nombre");
        query.setParameter("nombre", nombre+"%");
        List<Usuario> lista = query.getResultList();
        return lista;
    }

    public List<Usuario> consultaNativa(){
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("select * from usuario ", Usuario.class);
        //query.setParameter("nombre", apellido+"%");
        List<Usuario> lista = query.getResultList();
        return lista;
    }
}
