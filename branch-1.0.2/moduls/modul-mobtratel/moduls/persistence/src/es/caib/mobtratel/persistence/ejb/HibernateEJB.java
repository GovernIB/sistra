package es.caib.mobtratel.persistence.ejb;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.mobtratel.persistence.util.HibernateLocator;

/**
 * Bean con la funcionalidad básica para interactuar con HIBERNATE.
 *
 * @ejb.bean
 *  view-type="remote"
 *  generate="false"
 */
public abstract class HibernateEJB implements SessionBean {

    protected static Log log = LogFactory.getLog(HibernateEJB.class);

    protected SessionFactory sf = null;
    protected SessionContext ctx = null;

    public void setSessionContext(SessionContext ctx) {
        this.ctx = ctx;
    }

    public void ejbCreate() throws CreateException {
        log.debug("ejbCreate: " + this.getClass());
        sf = HibernateLocator.getSessionFactory();

    }

    public void ejbRemove() {
        log.debug("ejbRemove: " + this.getClass());
        sf = null;
    }

    protected Session getSession() {
        try {
            Session sessio = sf.openSession();
            return sessio;
        } catch (HibernateException e) {
            throw new EJBException(e);
        }
    }

    protected void close(Session sessio) {
        if (sessio != null && sessio.isOpen()) {
            try {
                if (sessio.isDirty()) {
                    sessio.flush();
                }                
            } catch (HibernateException e) {
                throw new EJBException(e);                               
            }finally{
            	try{
            		sessio.close();
            	}catch(HibernateException e){
            		throw new EJBException(e);
            	}
            }
            
        }
    }

}