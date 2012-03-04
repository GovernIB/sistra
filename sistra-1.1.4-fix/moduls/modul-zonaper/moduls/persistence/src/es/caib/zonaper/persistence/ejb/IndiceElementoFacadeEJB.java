package es.caib.zonaper.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.zonaper.model.IndiceElemento;

/**
 * SessionBean para mantener y consultar IndiceElemento
 * 
 *  @ejb.bean
 *  name="zonaper/persistence/IndiceElementoFacade"
 *  jndi-name="es.caib.zonaper.persistence.IndiceElementoFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 * 
 */
public abstract class IndiceElementoFacadeEJB extends HibernateEJB {
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();	
	}
 
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public IndiceElemento obtenerIndiceElemento(Long id) {
        Session session = getSession();
        try {
        	// Cargamos IndiceElemento        	
        	IndiceElemento registro = (IndiceElemento) session.load(IndiceElemento.class, id);                               	
            return registro;
        } catch (HibernateException he) {
        	throw new EJBException(he);
        } finally {
            close(session);
        }
    }           
    
 	/**
 	 * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public Long grabarIndiceElemento(IndiceElemento indiceElemento) {        
    	Session session = getSession();
        try {     
        	
        	// Protegemos ante valores que sobrepasen maximo
        	if (indiceElemento.getValor() != null && indiceElemento.getValor().length() > 4000 ) {
        		indiceElemento.setValor(indiceElemento.getValor().substring(0, 4000));
        	}
        	
        	session.save(indiceElemento);        	      	                    
            return indiceElemento.getCodigo();
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
        	
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public void borrarIndiceElemento(Long id) {        
    	Session session = getSession();
        try {     
        	IndiceElemento registro = (IndiceElemento) session.load(IndiceElemento.class, id);
        	session.delete(registro);
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
        	
            close(session);
        }
    }
    
    /**
     * Busca en los indices del usuario autenticado por la palabra clave.
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public List buscarIndice(String nif, String palabraClave) {
    	Session session = getSession();
		try
		{
			Query query = session
			.createQuery( "FROM IndiceElemento e where e.nif= :nif and upper(e.valor) like :clave" )
			.setParameter("nif",nif)
			.setParameter("clave","%" +  palabraClave.toUpperCase() + "%");
			return query.list();	
		}
		catch (HibernateException he) 
		{   
			throw new EJBException(he);
	    } 
		finally 
		{
	        close(session);
	    }
    }
}
