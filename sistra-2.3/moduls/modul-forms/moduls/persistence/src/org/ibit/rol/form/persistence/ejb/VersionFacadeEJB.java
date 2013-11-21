package org.ibit.rol.form.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import org.ibit.rol.form.model.Version;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

/**
 * SessionBean para matener y consultar máscaras de validación.
 *
 * @ejb.bean
 *  name="form/persistence/VersionFacade"
 *  jndi-name="org.ibit.rol.form.persistence.VersionFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class VersionFacadeEJB extends HibernateEJB {

    /**
     * @ejb.create-method
     * @ejb.permission unchecked="true"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
    }

    /**
     * Lista todas las máscaras.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador},${role.admin}"
     */
    public List listar() {
        Session session = getSession();
        try {
            //Criteria criteri = session.createCriteria(Mascara.class);
            //return criteri.list();
            Query query = session.createQuery("from Version m");
            query.setCacheable(true);
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    
    /**
     * Obtiene version
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador},${role.admin}"
     */
    public Version obtener(Long id) {
        Session session = getSession();
        try {
        	Version v = (Version) session.load(Version.class,id);
            return v;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

}
