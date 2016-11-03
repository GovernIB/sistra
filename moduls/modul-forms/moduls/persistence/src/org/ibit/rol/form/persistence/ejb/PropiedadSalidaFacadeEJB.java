package org.ibit.rol.form.persistence.ejb;

import net.sf.hibernate.Session;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import java.util.Set;
import java.util.List;

import org.ibit.rol.form.model.Salida;
import org.ibit.rol.form.model.PropiedadSalida;
import org.ibit.rol.form.model.Archivo;

/**
 * SessionBean para mantener y consultar salidas
 *
 * @ejb.bean
 *  name="form/persistence/PropiedadSalidaFacade"
 *  jndi-name="org.ibit.rol.form.persistence.PropiedadSalidaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class PropiedadSalidaFacadeEJB extends HibernateEJB{

    /**
    * @ejb.create-method
    * @ejb.permission unchecked="true"
    */
   public void ejbCreate() throws CreateException {
       super.ejbCreate();
   }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     */
    public Long grabarPropiedadSalida(PropiedadSalida propiedad, Long salida_id) {
        Session session = getSession();
        try {
            if (propiedad.getId() == null) {
                Salida salida = (Salida) session.load(Salida.class, salida_id);
                salida.addPropiedad(propiedad);
                session.flush();
            } else {
                session.update(propiedad);
            }
            return propiedad.getId();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }


     /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     */
   public void borrarPropiedadSalida(Long id) {
        Session session = getSession();
        try {
            PropiedadSalida propiedadSalida = (PropiedadSalida) session.load(PropiedadSalida.class, id);
            propiedadSalida.getSalida().removePropiedad(propiedadSalida);
            session.delete(propiedadSalida);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }


    /**
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public Set listarPropiedadesSalida(Long idSalida) {
        Session session = getSession();
        try {
            Salida salida = (Salida)session.load(Salida.class, idSalida);
            Hibernate.initialize(salida.getPropiedades());
            return salida.getPropiedades();

        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }


    /**
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public PropiedadSalida obtenerPropiedadSalida(Long id) {
        Session session = getSession();
        try {
            PropiedadSalida propiedadSalida = (PropiedadSalida)session.load(PropiedadSalida.class, id);
            Hibernate.initialize(propiedadSalida.getPlantilla());
            return propiedadSalida;

        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     */
    public void borrarPlantilla(Long id_propiedadSalida, Long id_plantilla) {
        Session session = getSession();
        try {
            PropiedadSalida propiedadSalida = (PropiedadSalida) session.load(PropiedadSalida.class, id_propiedadSalida);
            Query query1 = session.createQuery("FROM PropiedadSalida AS f WHERE f.id = :idpro");
            query1.setParameter("idpro", id_propiedadSalida);
            query1.setCacheable(true);
            List result1 = query1.list();
            int llista1 = result1.size();
            if (llista1 > 0) {
                propiedadSalida.setPlantilla(null);
                session.update(propiedadSalida);
                session.flush();
            }

            Archivo archivo = (Archivo) session.load(Archivo.class, id_plantilla);
            Query query2 = session.createQuery("FROM Archivo AS a WHERE a.id = :idarc");
            query2.setParameter("idarc", id_plantilla);
            query2.setCacheable(true);
            List result2 = query2.list();
            int llista2 = result2.size();
            if (llista2 > 0) {
                session.delete(archivo);
            }
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }


}
