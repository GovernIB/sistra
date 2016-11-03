package es.caib.sistra.persistence.ejb;

import java.util.Iterator;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import es.caib.sistra.model.Documento;
import es.caib.sistra.model.DocumentoNivel;

/**
 * SessionBean para mantener y consultar DocumentoNivel
 *
 * @ejb.bean
 *  name="sistra/persistence/DocumentoNivelFacade"
 *  jndi-name="es.caib.sistra.persistence.DocumentoNivelFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class DocumentoNivelFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.operador}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     */
    public DocumentoNivel obtenerDocumentoNivel(Long id) {
        Session session = getSession();
        try {
        	// Cargamos documentoNivel        	
        	DocumentoNivel documentoNivel = (DocumentoNivel) session.load(DocumentoNivel.class, id);
        	
            return documentoNivel;
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
    public Long grabarDocumentoNivel(DocumentoNivel obj,Long idDocumento) {        
    	Session session = getSession();
        try {        	
        	Documento documento = (Documento) session.load(Documento.class, idDocumento);
        	
        	// Realizamos actualización
        	if (obj.getCodigo() == null) {
                documento.addDocumentoNivel(obj);
                session.flush();
            } else {            	
                session.update(obj);
            }
        	        	
        	// Comprobamos que no existan niveles duplicados        	
        	String niveles = "";
        	for (Iterator it = documento.getNiveles().iterator();it.hasNext();){
        		String nivelDoc = (String) ((DocumentoNivel) it.next()).getNivelAutenticacion();        		
        		niveles = niveles + nivelDoc;        		
        	}        	
        	if  (
        			niveles.indexOf(DocumentoNivel.AUTENTICACION_ANONIMO) != niveles.lastIndexOf(DocumentoNivel.AUTENTICACION_ANONIMO) ||
        			niveles.indexOf(DocumentoNivel.AUTENTICACION_CERTIFICADO) != niveles.lastIndexOf(DocumentoNivel.AUTENTICACION_CERTIFICADO) ||
        			niveles.indexOf(DocumentoNivel.AUTENTICACION_USUARIOPASSWORD) != niveles.lastIndexOf(DocumentoNivel.AUTENTICACION_USUARIOPASSWORD) 
        		)
        	{
        		throw new HibernateException("No se pueden duplicar niveles de autenticación");
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
     * @ejb.permission role-name="${role.operador}"
     */
    public Set listarNivelesDocumento(Long idDocumento) {
        Session session = getSession();
        try {       	
        	Documento documento = (Documento) session.load(Documento.class, idDocumento);
            Hibernate.initialize(documento.getNiveles());
            return documento.getNiveles();   
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
    public void borrarDocumentoNivel(Long id) {
        Session session = getSession();
        try {
            DocumentoNivel documentoNivel = (DocumentoNivel) session.load(DocumentoNivel.class, id);
            documentoNivel.getDocumento().removeDocumentoNivel(documentoNivel);            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
}
