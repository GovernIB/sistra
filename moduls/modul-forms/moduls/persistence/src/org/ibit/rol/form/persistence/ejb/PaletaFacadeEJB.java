package org.ibit.rol.form.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import org.ibit.rol.form.model.Paleta;

/**
 * SessionBean para mantener y consultar paletas de componentes.
 *
 * @ejb.bean
 *  name="form/persistence/PaletaFacade"
 *  jndi-name="org.ibit.rol.form.persistence.PaletaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class PaletaFacadeEJB extends HibernateEJB {

    public void setSessionContext(SessionContext ctx) {
        super.setSessionContext(ctx);
    }

    /**
     * @ejb.create-method
     * @ejb.permission unchecked="true"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public Long gravarPaleta(Paleta paleta) {
        Session session = getSession();
        try {
            session.saveOrUpdate(paleta);
            session.flush();
            return paleta.getId();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin},${role.form}"
     */
    public List/*Paleta*/ listarPaletas() {
        Session session = getSession();
        try {
            Criteria criteri = session.createCriteria(Paleta.class);
            criteri.setCacheable(true);
            return criteri.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin},${role.form}"
     */
    public Paleta obtenerPaleta(Long id) {
        Session session = getSession();
        try {
            Paleta paleta = (Paleta) session.load(Paleta.class, id);
            Hibernate.initialize(paleta.getComponentes());
            return paleta;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void borrarPaleta(Long id) {
        Session session = getSession();
        try {
            Paleta paleta = (Paleta) session.load(Paleta.class, id);
            session.delete(paleta);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
}
