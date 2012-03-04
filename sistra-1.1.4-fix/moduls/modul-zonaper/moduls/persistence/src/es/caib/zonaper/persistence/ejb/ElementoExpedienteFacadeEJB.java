package es.caib.zonaper.persistence.ejb;

import java.security.Principal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.lang.StringUtils;

import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.CredentialUtil;
import es.caib.zonaper.model.CodigoElementoExpediente;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.ElementoExpedienteItf;
import es.caib.zonaper.model.Expediente;
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
			ElementoExpediente elemento = recuperarElementoExpedienteAutenticado(tipoElemento,codigoElemento);
			return elemento;
		} catch (Exception e) {
			throw new EJBException(e);
		}					
		
	}	
	
	/**
	 * Acceso autenticado. Solo accesible por usuario y delegados.
	 * 
	 * Recupera lista de ids de expedientes asociadas a los elementos. 
	 * Retorna Map con key=codigo elemento y value=id expediente asociado. Si un elemento no tiene asociado expediente no estará en la map.
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public Map obtenerIdsExpedienteElementos( List codigosElementos )
	{	
		Session session = getSession();
        try 
        {
        	Map res = new HashMap();
        	
        	if (codigosElementos == null || codigosElementos.size() <= 0) {
        		return res;
        	}
        	
        	// Generamos where
        	StringBuilder where = new StringBuilder(codigosElementos.size()); 
        	for (int i=0;i<codigosElementos.size();i++) {
        		if (i > 0) {
        			where.append(" OR ");
        		}
        		where.append(" (e.tipoElemento = :tipo" + i + " AND e.codigoElemento = :codigo" + i + " ) " );        		
        	}
        	
        	// Generamos query para obtener lista de ids de expediente
			Query query = 
        		session.createQuery("FROM ElementoExpediente e WHERE " + where.toString()  );
			for (int i=0;i<codigosElementos.size();i++) {
				CodigoElementoExpediente cee =  (CodigoElementoExpediente) codigosElementos.get(i);
				query.setParameter("tipo" + i, cee.getTipo());
				query.setParameter("codigo" + i, cee.getCodigo());
			}
			   
			
			// Ejecutamos query
			List listaElem = query.list();
			for (Iterator it = listaElem.iterator();it.hasNext();) {
				ElementoExpediente ee = (ElementoExpediente) it.next();
				res.put(new CodigoElementoExpediente(ee.getTipoElemento(), ee.getCodigoElemento()), ee.getExpediente().getCodigo());
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
			ElementoExpediente elemento = recuperarElementoExpedienteAnonimo(tipoElemento,codigoElemento,idPersistencia);
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
			ElementoExpediente elemento = getElementoExpediente(tipoElemento,codigoElemento);
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
			ElementoExpediente elemento = recuperarElementoExpedienteAutenticado(tipo,codigo);
			if (elemento == null) return null;
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
			ElementoExpediente elemento = recuperarElementoExpedienteAnonimo(tipo,codigo,idPersistencia);
			if (elemento == null) return null;
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
			ElementoExpediente elementoExpediente = this.obtenerElementoExpediente(id);			
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
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public ElementoExpedienteItf obtenerDetalleElementoExpedienteAutenticado(Long id)
	{
		try{
			ElementoExpediente elementoExpediente = this.obtenerElementoExpediente(id);			
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
		try{
			ElementoExpediente elementoExpediente = obtenerElementoExpediente(id);			
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
	
	// ----------------------------------------------------------------------------------------------------------------
	// 		FUNCIONES AUXILIARES
	// ----------------------------------------------------------------------------------------------------------------
	
	private ElementoExpediente obtenerElementoExpediente( Long id )
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
	
	private ElementoExpediente recuperarElementoExpedienteAutenticado(String tipoElemento,Long codigoElemento) throws Exception{
		// Obtenemos elemento expediente
		ElementoExpediente ee = getElementoExpediente(tipoElemento,codigoElemento);
		if (ee == null){
			return null;
		}
		
		// Comprobamos que el expediente pertenezca al usuario o si es un delegado
		Principal sp =this.ctx.getCallerPrincipal();
		PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
		if (plgLogin.getMetodoAutenticacion(sp) == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO){
    		throw new HibernateException("Acceso solo permitido para autenticado");
    	}
		if (!plgLogin.getNif(sp).equals(ee.getExpediente().getNifRepresentante())){
			
			// Si no es el usuario quien accede miramos si es un delegado
        	String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(ee.getExpediente().getNifRepresentante());
        	if (StringUtils.isEmpty(permisos)){
        		throw new Exception("El elememto de expediente " +  tipoElemento + " - " + codigoElemento + " no pertenece al usuario ni es delegado - usuario " + sp.getName());
        	}
		}
		
		return ee;
	}
	
	private ElementoExpediente recuperarElementoExpedienteAnonimo(String tipoElemento,Long codigoElemento,String idPersistencia) throws Exception{
		// Obtenemos elemento expediente
		ElementoExpediente ee = getElementoExpediente(tipoElemento,codigoElemento);
		if (ee == null){
			return null;
		}
		
		// Intentamos obtener el expediente con el id de persistencia. Se realizara el control de acceso
		Expediente expediente = DelegateUtil.getExpedienteDelegate().obtenerExpedienteAnonimo(ee.getExpediente().getCodigo(), idPersistencia);
		if (expediente == null){
			throw new Exception("Elemento no pertenece al expediente");
		}
		
		return ee;
	}
	
	private ElementoExpediente getElementoExpediente(String tipoElemento,Long codigoElemento){
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

}
