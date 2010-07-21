package org.ibit.rol.form.persistence.ejb;

import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Query;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.Patron;

/**
 * SessionBean para matener y consultar patrones.
 *
 * @ejb.bean
 *  name="form/persistence/PatronFacade"
 *  jndi-name="org.ibit.rol.form.persistence.PatronFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class PatronFacadeEJB extends HibernateEJB {

    /**
     * @ejb.create-method
     * @ejb.permission unchecked="true"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
    }

    /**
     * Crea o actualiza un patrón.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public Long gravarPatron(Patron patron) {
        Session session = getSession();
        try {
            session.saveOrUpdate(patron);
            return patron.getId();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Lista todos los patrones.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form},${role.admin}"
     */
    public List listarPatrones() {
        Session session = getSession();
        try {
            //Criteria criteri = session.createCriteria(Patron.class);
            //return criteri.list();
            Query query = session.createQuery("from Patron p");
            query.setCacheable(true);
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Obtiene un patrón.
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public Patron obtenerPatron(Long id) {
        Session session = getSession();
        try {
            Patron patron = (Patron) session.load(Patron.class, id);
            return patron;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Borra un patrón.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void borrarPatron(Long id) {
        Session session = getSession();
        try {
            Patron patron = (Patron) session.load(Patron.class, id);
            Iterator iCampos = patron.getCampos().iterator();
            while (iCampos.hasNext()) {
                Campo campo = (Campo) iCampos.next();
                campo.setPatron(null);
            }
            patron.getCampos().clear();
            session.delete(patron);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

}
