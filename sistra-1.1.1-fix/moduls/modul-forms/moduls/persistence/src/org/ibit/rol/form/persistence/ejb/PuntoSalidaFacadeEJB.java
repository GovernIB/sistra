package org.ibit.rol.form.persistence.ejb;

import net.sf.hibernate.Session;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import org.ibit.rol.form.model.PuntoSalida;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.Salida;

import java.util.List;
import java.util.ArrayList;

/**
 * SessionBean para matener y consultar Puntos Salida
 *
 * @ejb.bean
 *  name="form/persistence/PuntoSalidaFacade"
 *  jndi-name="org.ibit.rol.form.persistence.PuntoSalidaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class PuntoSalidaFacadeEJB extends HibernateEJB{

    /**
     * @ejb.create-method
     * @ejb.permission unchecked="true"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
    }

    /**
     * Crea o actualiza un punto salida
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public Long gravarPuntoSalida(PuntoSalida puntoSalida) {
        Session session = getSession();
        try {
            session.saveOrUpdate(puntoSalida);
            return puntoSalida.getId();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Lista todos los puntos salida
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public List listarPuntosSalida() {
        Session session = getSession();
        try {
            Query query = session.createQuery("from PuntoSalida p");
            query.setCacheable(true);
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Lista todos los puntos salida de un formulario
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public List listarPuntosSalidaFormulario(Long idFormulario) {
        Session session = getSession();
        try {
            List puntosSalida = new ArrayList();
            Formulario formulario = (Formulario)session.load(Formulario.class, idFormulario);
            List salidas = formulario.getSalidas();
            for (int i = 0; i < salidas.size(); i++) {
                Salida salida = (Salida) salidas.get(i);
                puntosSalida.add(salida.getPunto());
            }
            return puntosSalida;

        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Obtiene un punto salida
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public PuntoSalida obtenerPuntoSalida(Long id) {
        Session session = getSession();
        try {
            PuntoSalida puntoSalida = (PuntoSalida) session.load(PuntoSalida.class, id);
            return puntoSalida;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Borra un punto salida
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void borrarPuntoSalida(Long id) {
        Session session = getSession();
        try {
            PuntoSalida puntoSalida = (PuntoSalida)session.load(PuntoSalida.class, id);
            Query query = session.createQuery("from  Salida s where s.punto.id = :id");
            query.setParameter("id", id);
            if(!query.list().isEmpty()){
                throw new EJBException("El puntoSalida tiene formularios asociados");
            }
            session.delete(puntoSalida);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

}
