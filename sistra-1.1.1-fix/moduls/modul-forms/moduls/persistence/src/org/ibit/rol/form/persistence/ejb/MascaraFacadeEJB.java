package org.ibit.rol.form.persistence.ejb;

import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Query;
import org.ibit.rol.form.model.Mascara;
import org.ibit.rol.form.model.Validacion;

/**
 * SessionBean para matener y consultar máscaras de validación.
 *
 * @ejb.bean
 *  name="form/persistence/MascaraFacade"
 *  jndi-name="org.ibit.rol.form.persistence.MascaraFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class MascaraFacadeEJB extends HibernateEJB {

    /**
     * @ejb.create-method
     * @ejb.permission unchecked="true"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
    }

    /**
     * Crea o actualiza una máscara.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void gravarMascara(Mascara mascara) {
        Session session = getSession();
        try {
            session.saveOrUpdate(mascara);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Lista todas las máscaras.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador},${role.admin}"
     */
    public List listarMascaras() {
        Session session = getSession();
        try {
            //Criteria criteri = session.createCriteria(Mascara.class);
            //return criteri.list();
            Query query = session.createQuery("from Mascara m");
            query.setCacheable(true);
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Obtiene una máscara.
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public Mascara obtenerMascara(Long id) {
        Session session = getSession();
        try {
            Mascara mascara = (Mascara) session.load(Mascara.class, id);
            return mascara;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Borra una máscara.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void borrarMascara(Long id) {
        Session session = getSession();
        try {
            Mascara mascara = (Mascara) session.load(Mascara.class, id);
            for (Iterator iterator = mascara.getValidaciones().iterator(); iterator.hasNext();) {
                Validacion validacion = (Validacion) iterator.next();
                validacion.getCampo().removeValidacion(validacion);
                session.delete(validacion);
            }
            mascara.getValidaciones().clear();
            session.delete(mascara);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

}
