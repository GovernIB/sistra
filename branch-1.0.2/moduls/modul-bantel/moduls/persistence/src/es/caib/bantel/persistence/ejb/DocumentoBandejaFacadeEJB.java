package es.caib.bantel.persistence.ejb;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import es.caib.bantel.model.*;

/**
 * SessionBean para mantener y consultar DocumentoBandeja
 *
 * @ejb.bean
 *  name="bantel/persistence/DocumentoBandejaFacade"
 *  jndi-name="es.caib.bantel.persistence.DocumentoBandejaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class DocumentoBandejaFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.bantel}"
     * @ejb.permission role-name="${role.gestor}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.bantel}"
     * @ejb.permission role-name="${role.gestor}"
     */
    public DocumentoBandeja obtenerDocumentoBandeja(Long id) {
        Session session = getSession();
        try {
        	// Cargamos DocumentoBandeja        	
        	DocumentoBandeja DocumentoBandeja = (DocumentoBandeja) session.load(DocumentoBandeja.class, id);
        	return DocumentoBandeja;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
        
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.bantel}"
     */
    public Long grabarDocumentoBandeja(DocumentoBandeja obj,Long id) {        
    	Session session = getSession();
        try {        	
        	if (obj.getCodigo() == null) {
            	TramiteBandeja tram = (TramiteBandeja) session.load(TramiteBandeja.class,id);
            	tram.addDocumento(obj);   
                session.flush();
            } else {
                session.update(obj);
            }                    	
            return obj.getCodigo();            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
        	
            close(session);
        }
    }
    
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.bantel}"
     */
    public void borrarDocumentoBandeja(Long id) {
        Session session = getSession();
        try {
            DocumentoBandeja DocumentoBandeja = (DocumentoBandeja) session.load(DocumentoBandeja.class, id);
            DocumentoBandeja.getTramite().removeDocumento(DocumentoBandeja);            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
}
