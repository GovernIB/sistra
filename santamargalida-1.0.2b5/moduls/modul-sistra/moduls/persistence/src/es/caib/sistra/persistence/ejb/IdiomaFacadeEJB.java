package es.caib.sistra.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Query;

/**
 * SessionBean para consultar idiomas.
 *
 * @ejb.bean
 *  name="sistra/persistence/IdiomaFacade"
 *  jndi-name="es.caib.sistra.persistence.IdiomaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class IdiomaFacadeEJB extends HibernateEJB {

    /**
     * @ejb.create-method
     * @ejb.permission unchecked="true"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
    }

    /**
     * Devuelve una lista de {@link java.lang.String} con el codigo ISO los idiomas.
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public List /* String */ listarLenguajes() {
        Session session = getSession();
        try {
            Query query = session.createQuery("select idi.lang from Idioma as idi order by idi.orden");
            query.setCacheable(true);
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Obtiene el codigo ISO del lenguaje por defecto.
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public String lenguajePorDefecto() {
        Session session = getSession();
        try {
            Query query = session.createQuery("select idi.lang from Idioma as idi where idi.orden = 0");
            query.setCacheable(true);
            List results = query.list();

            if (results.isEmpty()) {
                return null;
            } else {
                return (String) results.get(0);
            }
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
}
