package es.caib.zonaper.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.zonaper.model.LogRegistro;
import es.caib.zonaper.model.LogRegistroId;

/**
 * SessionBean para mantener y consultar RegistroExterno
 *
 * @ejb.bean
 *  name="zonaper/persistence/LogRegistroFacade"
 *  jndi-name="es.caib.zonaper.persistence.LogRegistroFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class LogRegistroFacadeEJB extends HibernateEJB {

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
     * @ejb.permission role-name="${role.auto}"
     */
    public LogRegistro obtenerLogRegistro(LogRegistroId id) {
        Session session = getSession();
        try {
        	// Cargamos LogExterno        	
        	LogRegistro logRegistro = (LogRegistro) session.load(LogRegistro.class, id);                       
            return logRegistro;
        } catch(ObjectNotFoundException ex){
        	return null;
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
    public void grabarLogRegistro(LogRegistro logRegistro) {        
    	Session session = getSession();
        try {  
        	if( obtenerLogRegistro(logRegistro.getId()) != null){
        		session.update(logRegistro);        		
        	}else{
        		session.save(logRegistro);
        	}
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
    public void borrarLogRegistro(LogRegistroId id) {        
    	Session session = getSession();
        try {     
        	session.delete(obtenerLogRegistro(id));
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
    public void borrarLogRegistro(LogRegistro log) {        
    	Session session = getSession();
        try {     
        	session.delete(log);
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
    public boolean tieneUsos(LogRegistro logReg) throws DelegateException {
        Session session = getSession();
        String hql ="";
        String hql2 ="";
        try { 
        	if(logReg.getId().getTipoRegistro().equals(ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA+"")){
        		hql  =	"SELECT count(ent) FROM EntradaTelematica AS ent WHERE ent.numeroRegistro = :numeroRegistro";
        		hql2 =	"SELECT count(rgext) FROM RegistroExterno AS rgext WHERE rgext.numeroRegistro = :numeroRegistro";
        	}else if(logReg.getId().getTipoRegistro().equals(ConstantesAsientoXML.TIPO_PREREGISTRO+"")){
				hql =	"SELECT count(entpre) FROM EntradaPreregistro AS entpre WHERE entpre.numeroRegistro = :numeroRegistro";
        	}else if(logReg.getId().getTipoRegistro().equals(ConstantesAsientoXML.TIPO_REGISTRO_SALIDA+"")){
    			hql =	"SELECT count(not) FROM NotificacionTelematica AS not WHERE not.numeroRegistro = :numeroRegistro";
        	}
        	Query query = session.createQuery(hql);
        	query.setString("numeroRegistro",logReg.getId().getNumeroRegistro());
        	List logRegs = query.list();
        	Integer valor = (Integer) logRegs.get(0);
        	/*si el valor del count es mas grande que 0 quiere decir que tiene usos, devolvemos un true
        	 *si el valor del count es 0 tenemos dos posibilidades:
    		 *	
    		 *	1.- si es un preregistro o un registro de salida quiere decir que no tiene usos, devolvemos 
    		 *		directamente un false
    		 *	2.- si es una entrada tendremos que hacer la comprovación para la tabla de registros externos.	 
    		 */
    		if(valor.intValue() > 0){
        		return true;
        	}else{
        		if(logReg.getId().getTipoRegistro().equals(ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA+"")){
        			query = session.createQuery(hql2);
        			query.setString("numeroRegistro",logReg.getId().getNumeroRegistro());
                	logRegs = query.list();
                	valor = (Integer) logRegs.get(0);
                	if(valor.intValue() > 0){
        				return true;
        			}
        		}
        		return false;
        	}
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
    public List listarLogRegistro() throws DelegateException {
        Session session = getSession();    	    	    	
        try {        	        
        	Query query = session.createQuery("FROM LogRegistro AS lr WHERE lr.anulado != 'S'");
            return query.list();
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {        	        
            close(session);
        }                
    }
}
