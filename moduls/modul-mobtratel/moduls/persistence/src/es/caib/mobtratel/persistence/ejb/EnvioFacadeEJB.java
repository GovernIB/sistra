package es.caib.mobtratel.persistence.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.expression.Order;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.mobtratel.model.CriteriosBusquedaEnvio;
import es.caib.mobtratel.model.Cuenta;
import es.caib.mobtratel.model.Envio;
import es.caib.mobtratel.model.Page;
import es.caib.mobtratel.model.Permiso;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.PermisoDelegate;
import es.caib.mobtratel.persistence.util.CacheProcesamiento;

/**
 * SessionBean para mantener y consultar Envios en la BBDD.
 *
 * @ejb.bean
 *  name="mobtratel/persistence/EnvioFacade"
 *  jndi-name="es.caib.mobtratel.persistence.EnvioFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class EnvioFacadeEJB extends HibernateEJB {

	protected static Log log = LogFactory.getLog(EnvioFacadeEJB.class);
	
    /**
     * @ejb.create-method
     * @ejb.permission role-name="${role.gestor},${role.mobtratel},${role.auto}"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public List listarPendientes() {
        Session session = getSession();
        Date now = new Date();
        try {
        	Query query = session.createQuery("FROM Envio AS e where ((e.fechaProgramacionEnvio <= :fechaActual) or e.fechaProgramacionEnvio is null) and e.fechaEnvio is null  " +
        			" and ((e.estado = " + Envio.PENDIENTE_ENVIO + ") or (e.estado = " + Envio.CON_ERROR + ")  or (e.estado = " + Envio.PROCESANDOSE + "))  " +
        			" ORDER BY e.fechaProgramacionEnvio DESC,e.codigo DESC");
            query.setParameter("fechaActual", now);            
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } 
        finally 
        {
            close(session);
        }
    }
    
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public List listarInmediatosPendientes() {
    	Session session = getSession();
        Date now = new Date();
        try {
        	Query query = session.createQuery("FROM Envio AS e where (e.inmediato = 1) and e.fechaEnvio is null " + 
        			" and ((e.estado = " + Envio.PENDIENTE_ENVIO + ") or (e.estado = " + Envio.CON_ERROR + ")  or (e.estado = " + Envio.PROCESANDOSE + "))  " +
        			" ORDER BY e.fechaProgramacionEnvio DESC");
            query.setParameter("fechaActual", now);            
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } 
        finally 
        {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor},${role.mobtratel},${role.auto}"
     */
    public Envio obtenerEnvio(Long codigo) {
        Session session = getSession();
        try {
        	// Cargamos envio        	
        	Envio envio = (Envio) session.load(Envio.class, codigo);
        	return envio;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor},${role.mobtratel},${role.auto}"
     */
    public Long grabarEnvio(Envio obj) {        
    	Session session = getSession();
    	try {
    		if (obj.getCodigo() == null)
    			session.save(obj);
    		else
    			session.update(obj);        	
        	return obj.getCodigo();            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
        	close(session);
        }
    }  
    
    /**
     * Cancelar envio desde el front. Comprueba que el envio no este bloqueado por proceso de envio.
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor},${role.mobtratel}"
     */
    public boolean cancelarEnvio(Long idEnvio) {        
    	Session session = getSession();
    	boolean locked=false;
    	try {
        	
    		if (!CacheProcesamiento.guardar(idEnvio.toString())){
    			return false;
    		}
    		locked = true;
    		
    		Envio envio = (Envio) session.load(Envio.class, idEnvio);
    		
    		// El envio no puede cancelarse si ya esta enviado o esta cancelado
    		if (envio.getEstado() == Envio.ENVIADO || envio.getEstado() == Envio.CANCELADO ){
    			throw new HibernateException("El envio esta en estado " + envio.getEstado() + ". No puede cancelarse.");
    		}
    		
    		envio.setEstado(Envio.CANCELADO);
    		session.update(envio);
        	return true;            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
        	if (locked) CacheProcesamiento.borrar(idEnvio.toString());
        	close(session);
        }
    }  

    
	/**
	 * Comprueba si un envio se esta enviando
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor},${role.mobtratel}"
     */
	public boolean isEnviando(Long idEnvio){
		return CacheProcesamiento.existe(idEnvio.toString());
	}
	
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor},${role.mobtratel}"
     */
    public Page busquedaPaginadaEnvios( CriteriosBusquedaEnvio criteriosBusqueda, int pagina, int longitudPagina )
    {
    	
    	try{
	    	// Obtenemos datos gestor por Usuario Seycon
	    	PermisoDelegate gd = DelegateUtil.getPermisoDelegate();
	    	List permisos = gd.listarPermisos(this.ctx.getCallerPrincipal().getName());
	    	if (permisos.size() == 0) throw new Exception("No se encuentra gestor para usuario seycon " + this.ctx.getCallerPrincipal().getName());
    	}catch (Exception he) 
		{
	        throw new EJBException(he);
	    } 

    	    	
    	// Es posible que haya que sustituir Criteria por Query, con la from clause construida de forma dinámica
    	// Seria necesario si en la paginacion se necesita saber el numero maximo de paginas para 
		Session session = getSession();
		try 
		{			
			Criteria criteria =createCriteriaFromCriteriosBusquedaEnvio(criteriosBusqueda,session);	
			Page page = new Page( criteria, pagina, longitudPagina );
			
			/*
			Query q =createQueryFromCriteriosBusquedaTramite(criteriosBusqueda,session);			
			Page page = new Page( q, pagina, longitudPagina );					
			*/
			
			if(page.getList().size() == 0) return null;
			
			return page;
			
	    } 
		catch (Exception he) 
		{
	        throw new EJBException(he);
	    } 
		finally 
	    {
	        close(session);
	    }
    }

	
    private Criteria createCriteriaFromCriteriosBusquedaEnvio(CriteriosBusquedaEnvio criteriosBusqueda,Session session) throws Exception{
    	    	    
    	// Obtenemos cuentas accesibles por gestor
    	List cuentasAccesibles = new ArrayList();
    	try{	    	
    		PermisoDelegate gd = DelegateUtil.getPermisoDelegate();
	    	List permisos = gd.listarPermisos(this.ctx.getCallerPrincipal().getName());
	    	if (permisos.size() == 0) throw new Exception("No se encuentra gestor para usuario seycon " + this.ctx.getCallerPrincipal().getName());
	    	
	    	
	    	for (Iterator it = permisos.iterator();it.hasNext();){
	    		Permiso per =  (Permiso) it.next();
	    		cuentasAccesibles.add(per.getCuenta());
	    	}
	    	
    	}catch (Exception he) 
		{
	        throw new EJBException(he);
	    }
    	
    	Criteria criteria = session.createCriteria( Envio.class );
    	criteria.setCacheable( false );
 
		 //Especificamos estado procesamiento entrada
		 if ( !criteriosBusqueda.getEnviado().equals(CriteriosBusquedaEnvio.TODOS)  )
		 {
			 if (criteriosBusqueda.getEnviado().equals(String.valueOf(Envio.ENVIADO))  )
				 criteria.add( Expression.eq( "estado" , new Integer(Envio.ENVIADO) ) );
			 if (criteriosBusqueda.getEnviado().equals(String.valueOf(Envio.CANCELADO))  )
				 criteria.add( Expression.eq( "estado" , new Integer(Envio.CANCELADO)) );
			 if (criteriosBusqueda.getEnviado().equals(String.valueOf(Envio.CON_ERROR))  )
				 criteria.add( Expression.eq( "estado", new Integer(Envio.CON_ERROR) ) );
			 if (criteriosBusqueda.getEnviado().equals(String.valueOf(Envio.PENDIENTE_ENVIO))  )
				 criteria.add( Expression.eq( "estado", new Integer(Envio.PENDIENTE_ENVIO) ) );				 
		 }
		
		 // Especificamos cuenta particular 
		 if ( !criteriosBusqueda.getCuenta().equals(CriteriosBusquedaEnvio.TODOS)) 
		 {
			 Cuenta cuenta = (Cuenta) session.load(Cuenta.class,criteriosBusqueda.getCuenta());
			 criteria.add( Expression.eq("cuenta", cuenta ) );
		 }else{
		 // Especificamos cuentas a las que tiene acceso
			criteria.add(Expression.in("cuenta",cuentasAccesibles)); 
		 }
		 
		 // Especificamos fecha
		 if ( criteriosBusqueda.getAnyo() != 0 )
		 {
			 GregorianCalendar gregorianCalendar1 = null;
			 GregorianCalendar gregorianCalendar2 = null;
			 if ( criteriosBusqueda.getMes() == -1 )
			 {
			 	 gregorianCalendar1 = new GregorianCalendar( criteriosBusqueda.getAnyo(), 0, 1 );
			 	 gregorianCalendar2 = new GregorianCalendar( criteriosBusqueda.getAnyo(), 11, 31 );
			 }
			 else
			 {
				 gregorianCalendar1 = new GregorianCalendar( criteriosBusqueda.getAnyo(), criteriosBusqueda.getMes(), 1 );
				 int year =  criteriosBusqueda.getAnyo();
				 int month = criteriosBusqueda.getMes();
				 gregorianCalendar2 = new GregorianCalendar( year, month, gregorianCalendar1.getMaximum( GregorianCalendar.DAY_OF_MONTH ) );
			 }
			 criteria.add( Expression.between( "fechaRegistro", new java.sql.Date(gregorianCalendar1.getTime().getTime()), new java.sql.Date( gregorianCalendar2.getTime().getTime() )) );
		 }
		
		 // Ordenación
		 criteria.addOrder( Order.desc("fechaRegistro") );
		 return criteria;
    }    
    
}