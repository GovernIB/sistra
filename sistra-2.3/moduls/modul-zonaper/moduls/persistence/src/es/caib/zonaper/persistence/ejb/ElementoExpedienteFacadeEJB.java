package es.caib.zonaper.persistence.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.ElementoExpedienteItf;
import es.caib.zonaper.model.IndiceElemento;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * SessionBean para mantener y consultar ElementoExpediente
 *
 * @ejb.bean
 *  name="zonaper/persistence/ElementoExpedienteFacade"
 *  jndi-name="es.caib.zonaper.persistence.ElementoExpedienteFacade"
 *  type="Stateless"
 *  view-type="both"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class ElementoExpedienteFacadeEJB extends HibernateEJB
{
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"    
     */
	public void ejbCreate() throws CreateException 
	{
		super.ejbCreate();
	}
		
	/**
	 * Acceso autenticado. Solo accesible por usuario y delegados.
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public ElementoExpediente obtenerElementoExpedienteAutenticado( String tipoElemento,Long codigoElemento )
	{	
		try {
			ElementoExpediente elemento = recuperarElementoExpedientePorTipoCodigo(tipoElemento,codigoElemento);
			
			// Verificamos acceso a expediente
			if (elemento != null && !DelegateUtil.getExpedienteDelegate().verificarAccesoExpedienteAutenticado(elemento.getExpediente().getCodigo())) {
				throw new Exception("No tiene acceso al expediente");
			}
				
			return elemento;
		} catch (Exception e) {
			throw new EJBException(e);
		}					
		
	}	
	
	/**
	 * Acceso autenticado. Solo accesible por usuario y delegados.
	 * 
	 * Recupera lista de ids de expedientes asociadas a indices de busqueda. 
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public List obtenerIdsExpedienteElementos( List indicesBusqueda )
	{	
		Session session = getSession();
        try 
        {
        	List res = new ArrayList();
        	
        	if (indicesBusqueda == null || indicesBusqueda.size() <= 0) {
        		return res;
        	}
        	
        	// Generamos where
        	StringBuilder where = new StringBuilder(indicesBusqueda.size());
        	boolean primer = true;
        	for (int i=0;i<indicesBusqueda.size();i++) {
        		IndiceElemento ie = (IndiceElemento) indicesBusqueda.get(i);
				if (!ie.getTipoElemento().equals(IndiceElemento.TIPO_EXPEDIENTE)) {
					if (!primer) {
	        			where.append(" OR ");	        			
	        		}
	        		where.append(" (e.tipoElemento = :tipo" + i + " AND e.codigoElemento = :codigo" + i + " ) " );
	        		primer = false;
				}        		        		
        	}
        	
        	// Generamos query para obtener lista de ids de expediente        	
			Query query = 
        		session.createQuery("FROM ElementoExpediente e WHERE " + where.toString()  );
			for (int i=0;i<indicesBusqueda.size();i++) {
				IndiceElemento ie = (IndiceElemento) indicesBusqueda.get(i);
				if (ie.getTipoElemento().equals(IndiceElemento.TIPO_EXPEDIENTE)) {
					res.add(ie.getCodigoElemento());
				} else {
					query.setParameter("tipo" + i, ie.getTipoElemento());
					query.setParameter("codigo" + i, ie.getCodigoElemento());					
				}				
			}
			   
			// Ejecutamos query
			List listaElem = query.list();
			for (Iterator it = listaElem.iterator();it.hasNext();) {
				ElementoExpediente ee = (ElementoExpediente) it.next();
				res.add(ee.getExpediente().getCodigo());
			}
			
        	// Devolvemos map
			return res;
        }
        catch( HibernateException he )
        {
        	throw new EJBException( he );
        }
        catch( Exception exc )
        {
        	throw new EJBException( exc );
        }
        finally
        {
        	close( session );
        }
        
	}	
	
	/**
	 * Acceso anonimo
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public ElementoExpediente obtenerElementoExpedienteAnonimo( String tipoElemento,Long codigoElemento, String idPersistencia )
	{
		try {
			ElementoExpediente elemento = recuperarElementoExpedientePorTipoCodigo(tipoElemento,codigoElemento);
			
			// Verificamos acceso a expediente
			if (elemento != null && !DelegateUtil.getExpedienteDelegate().verificarAccesoExpedienteAnonimo(elemento.getExpediente().getCodigo(), idPersistencia)) {
				throw new Exception("No tiene acceso al expediente");
			}
			
			return elemento;
		} catch (Exception e) {
			throw new EJBException(e);
		}
	}	
	
	/**
	 * Acceso para gestor y procesos auto
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.gestor}"
     */
	public ElementoExpediente obtenerElementoExpediente( String tipoElemento,Long codigoElemento)
	{
		try {
			ElementoExpediente elemento = recuperarElementoExpedientePorTipoCodigo(tipoElemento,codigoElemento);
			return elemento;
		} catch (Exception e) {
			throw new EJBException(e);
		}
	}	
	
	
	/**
	 * Obtiene elemento expediente por su id persistencia
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public ElementoExpediente obtenerElementoExpediente( String idPersistencia )
	{
		try {
			ElementoExpediente elemento = recuperarElementoExpedientePorIdPersistencia(idPersistencia);
			return elemento;
		} catch (Exception e) {
			throw new EJBException(e);
		}
	}	
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public ElementoExpedienteItf obtenerDetalleElementoExpedienteAutenticado(String tipo, Long codigo)
	{
		try {
			ElementoExpediente elemento = recuperarElementoExpedientePorTipoCodigo(tipo,codigo);
			if (elemento == null) return null;
			// El control de acceso se realizara al acceder al elemento
			return this.obtenerDetalleElementoExpedienteAutenticado(elemento.getCodigo());	
		} catch (Exception e) {
			throw new EJBException(e);
		}	 				 	 
	}
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public ElementoExpedienteItf obtenerDetalleElementoExpedienteAnonimo(String tipo, Long codigo,String idPersistencia)
	{
		try {
			ElementoExpediente elemento = recuperarElementoExpedientePorTipoCodigo(tipo,codigo);
			if (elemento == null) return null;
			// El control de acceso se realizara al acceder al elemento
			return this.obtenerDetalleElementoExpedienteAnonimo(elemento.getCodigo(),idPersistencia);	
		} catch (Exception e) {
			throw new EJBException(e);
		}	
	}
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.gestor}"
     */
	public ElementoExpedienteItf obtenerDetalleElementoExpediente(Long id)
	{
		try{
			ElementoExpediente elementoExpediente = this.recuperarElementoExpedientePorCodigo(id);			
			if (ElementoExpediente.TIPO_ENTRADA_TELEMATICA.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getEntradaTelematicaDelegate().obtenerEntradaTelematica(elementoExpediente.getCodigoElemento());
			}else if (ElementoExpediente.TIPO_ENTRADA_PREREGISTRO.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistro(elementoExpediente.getCodigoElemento());
			}else if (ElementoExpediente.TIPO_AVISO_EXPEDIENTE.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getEventoExpedienteDelegate().obtenerEventoExpediente(elementoExpediente.getCodigoElemento());
			}else if (ElementoExpediente.TIPO_NOTIFICACION.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getNotificacionTelematicaDelegate().obtenerNotificacionTelematicaAuto(elementoExpediente.getCodigoElemento());
			}
			return null;
		}catch (Exception ex){
			throw new EJBException("Error obteniendo detalle del elemento expediente con codigo " + id,ex);
		}
	}
	
	
	/**
	 * Obtiene codigo expediente al que pertenece un elemento. No esta protegido específicamente ya que solo obtenemos el código.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.todos}"
     */
	public Long obtenerCodigoExpedienteElemento(String tipo, Long codigo)
	{
		try {
			ElementoExpediente elemento = recuperarElementoExpedientePorTipoCodigo(tipo,codigo);
			if (elemento == null) return null;
			return elemento.getExpediente().getCodigo();	
		} catch (Exception e) {
			throw new EJBException(e);
		}			
	}	
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public ElementoExpedienteItf obtenerDetalleElementoExpedienteAutenticado(Long id)
	{
		// El control de acceso se realizara al acceder al elemento
		try{
			ElementoExpediente elementoExpediente = this.recuperarElementoExpedientePorCodigo(id);			
			if (ElementoExpediente.TIPO_ENTRADA_TELEMATICA.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getEntradaTelematicaDelegate().obtenerEntradaTelematicaAutenticada(elementoExpediente.getCodigoElemento());
			}else if (ElementoExpediente.TIPO_ENTRADA_PREREGISTRO.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistroAutenticada(elementoExpediente.getCodigoElemento());
			}else if (ElementoExpediente.TIPO_AVISO_EXPEDIENTE.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getEventoExpedienteDelegate().obtenerEventoExpedienteAutenticado(elementoExpediente.getCodigoElemento());
			}else if (ElementoExpediente.TIPO_NOTIFICACION.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getNotificacionTelematicaDelegate().obtenerNotificacionTelematicaAutenticada(elementoExpediente.getCodigoElemento());
			}
			return null;
		}catch (Exception ex){
			throw new EJBException("Error obteniendo detalle del elemento expediente con codigo " + id,ex);
		}
	}
	
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public ElementoExpedienteItf obtenerDetalleElementoExpedienteAnonimo(Long id,String idPersistencia)
	{
		// El control de acceso se realizara al acceder al elemento
		try{
			ElementoExpediente elementoExpediente = recuperarElementoExpedientePorCodigo(id);			
			if (ElementoExpediente.TIPO_ENTRADA_TELEMATICA.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getEntradaTelematicaDelegate().obtenerEntradaTelematicaAnonima(elementoExpediente.getCodigoElemento(),idPersistencia);
			}else if (ElementoExpediente.TIPO_ENTRADA_PREREGISTRO.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistroAnonima(elementoExpediente.getCodigoElemento(),idPersistencia);
			}else if (ElementoExpediente.TIPO_AVISO_EXPEDIENTE.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getEventoExpedienteDelegate().obtenerEventoExpedienteAnonimo(elementoExpediente.getCodigoElemento(),idPersistencia);
			}else if (ElementoExpediente.TIPO_NOTIFICACION.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getNotificacionTelematicaDelegate().obtenerNotificacionTelematicaAnonima(elementoExpediente.getCodigoElemento(),idPersistencia);
			}
			return null;
		}catch (Exception ex){
			throw new EJBException("Error obteniendo detalle del elemento expediente con codigo " + id,ex);
		}
	}
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
	public void establecerAvisoElementoExpediente(Long id, String idAviso)
	{
		Session session = getSession();
		try
		{
			ElementoExpediente elementoExpediente = ( ElementoExpediente ) session.load( ElementoExpediente.class, id );			
			elementoExpediente.setCodigoAviso(idAviso);
			session.update(elementoExpediente);
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
	
	// ----------------------------------------------------------------------------------------------------------------
	// 		FUNCIONES AUXILIARES
	// ----------------------------------------------------------------------------------------------------------------
	
	private ElementoExpediente recuperarElementoExpedientePorCodigo( Long id )
	{
		Session session = getSession();
		try
		{
			ElementoExpediente elementoExpediente = ( ElementoExpediente ) session.load( ElementoExpediente.class, id );			
			return elementoExpediente;
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
	
	private ElementoExpediente recuperarElementoExpedientePorTipoCodigo(String tipoElemento,Long codigoElemento) throws Exception{
		// Obtenemos elemento expediente
		Session session = getSession();
		try
		{
			Query query = 
				session.createQuery( "FROM ElementoExpediente AS ee where ee.tipoElemento = :tipoElemento and ee.codigoElemento = :codigoElemento" ).
				setParameter("tipoElemento", tipoElemento).				
				setParameter("codigoElemento", codigoElemento);
			ElementoExpediente elementoExpediente = ( ElementoExpediente ) query.uniqueResult();			
			return elementoExpediente;
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
	
	private ElementoExpediente recuperarElementoExpedientePorIdPersistencia(String idPersistencia) throws Exception{
		// Obtenemos elemento expediente
		Session session = getSession();
		try
		{
			Query query = 
				session.createQuery( "FROM ElementoExpediente AS ee where ee.identificadorPersistencia = :identificadorPersistencia" ).
				setParameter("identificadorPersistencia", idPersistencia);
			ElementoExpediente elementoExpediente = ( ElementoExpediente ) query.uniqueResult();			
			return elementoExpediente;
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
