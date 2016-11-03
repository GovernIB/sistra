package es.caib.redose.persistence.ejb;

import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import es.caib.redose.model.Documento;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.util.HibernateLocator;

/**
 * Utilidad que sirve para resolver internamente referencias de documentos de las cuales
 * sólo tenemos el código. (Utilizado para acceder a datos propios)
 * Sólo podrá utilizarse internamente en el RDS.
 */
public class ResolveRDS {
	
	private static SessionFactory sf = null;
	private static ResolveRDS instance = null;
	
	public static ResolveRDS getInstance(){
		if (instance==null){
			instance = new ResolveRDS();
			sf = HibernateLocator.getSessionFactory();
		}
		return instance;
	}
		
	private Session getSession() {
        try {
            Session sessio = sf.openSession();
            return sessio;
        } catch (HibernateException e) {
            throw new EJBException(e);
        }
    }

	private void close(Session sessio) {
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

	/**
	 * Resuelve referencia RDS a partir del código de documento
	 * @param codigoRDS
	 * @return
	 */
	public ReferenciaRDS resuelveRDS(long codigoRDS) throws Exception{
		Session session = getSession();    	
    	try {	    	
    		Documento documento;
	    	documento = (Documento) session.load(Documento.class, new Long(codigoRDS));	    	
	    	ReferenciaRDS ref = new ReferenciaRDS();
	    	ref.setCodigo(codigoRDS);
	    	ref.setClave(documento.getClave());	    	
	    	return ref;
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        close(session);
	    } 
	}
}
