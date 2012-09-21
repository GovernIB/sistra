package es.caib.zonaper.persistence.ejb;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.lang.StringUtils;

import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.CredentialUtil;
import es.caib.zonaper.model.EstadoExpediente;
import es.caib.zonaper.model.Page;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * SessionBean para consultar EstadoExpediente
 *
 * @ejb.bean
 *  name="zonaper/persistence/EstadoExpedienteFacade"
 *  jndi-name="es.caib.zonaper.persistence.EstadoExpedienteFacade"
 *  type="Stateless"
 *  view-type="both"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class EstadoExpedienteFacadeEJB extends HibernateEJB
{
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.registro}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.gestor}"
     */
	public void ejbCreate() throws CreateException 
	{
		super.ejbCreate();
	}
	
	/**
	 * Realiza búsqueda paginada para expedientes del usuario logeado
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}" 
     */
	public Page busquedaPaginadaExpedientes(int pagina, int longitudPagina, List filtroExpe )
	{
		Session session = getSession();
        try 
        {
        	// Obtenemos nif usuario autenticado
        	String nifUser = PluginFactory.getInstance().getPluginLogin().getNif(this.ctx.getCallerPrincipal());
        	
        	// Construimos sql de filtro expedientes
        	String sqlFiltro = "";
        	if (filtroExpe != null) {
        		// Añadimos lista de expedientes	
	        	sqlFiltro = " AND e.id in (:filtroExpe) ";        		
        	}
        	
        	// Realizamos busqueda paginada
        	Query query = 
        		session.createQuery( 
        				"FROM EstadoExpediente AS e WHERE e.nifRepresentante = :nifUser and e.autenticado = 'S'" +
        				sqlFiltro + 
        				" ORDER BY e.fechaActualizacion DESC,e.id DESC" )
        		.setParameter("nifUser",nifUser);   
        	if (filtroExpe != null) {
        		if (filtroExpe.size() > 0) {
        	  		query.setParameterList("filtroExpe",filtroExpe);
        		} else {
        			// Si no se ha especificado ningun expediente en el filtro no debemos devolver ningun resultado
        			List listaVacia = new ArrayList();
        			listaVacia.add("");
        			query.setParameterList("filtroExpe",listaVacia);
        		}
        	}
            Page page = new Page( query, pagina, longitudPagina );
            return page;
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
	 * Realiza búsqueda paginada para expedientes de la entidad
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}" 
     */
	public Page busquedaPaginadaExpedientesEntidadDelegada(int pagina, int longitudPagina, String nifEntidad, List filtroExpe )
	{
		Session session = getSession();
        try 
        {
        	// Comprobamos si usuario esta autenticado
        	Principal sp = this.ctx.getCallerPrincipal(); 
        	PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
        	if (plgLogin.getMetodoAutenticacion(sp) == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO) throw new HibernateException("Debe estar autenticado");
        	
        	// 	Comprobamos que el usuario es delegado de la entidad
        	String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(nifEntidad);
        	if (StringUtils.isEmpty(permisos)){
        		throw new Exception("El usuario no es delegado de la entidad");
        	}
        	
        	// Construimos sql viendo si aplicamos filtro expedientes
        	String sqlFiltro = "";
        	if (filtroExpe != null) {
        		// Añadimos lista de expedientes	
        		sqlFiltro = " AND e.id in (:filtroExpe) "; 	        		        
        	}
        	
        	// Realizamos busqueda paginada
        	Query query = 
        		session.createQuery( 
        				"FROM EstadoExpediente AS e WHERE e.nifRepresentante = :nifEntidad and e.autenticado = 'S'" + 
        				sqlFiltro + 
        				" ORDER BY e.fechaActualizacion DESC,e.id DESC" )
        		.setParameter("nifEntidad",nifEntidad);    
        	if (filtroExpe != null) {
        		if (filtroExpe.size() > 0) {
        	  		query.setParameterList("filtroExpe",filtroExpe);
        		} else {
        			// Si no se ha especificado ningun expediente en el filtro no debemos devolver ningun resultado
        			List listaVacia = new ArrayList();
        			listaVacia.add("");
        			query.setParameterList("filtroExpe",listaVacia);
        		}
        	}
            Page page = new Page( query, pagina, longitudPagina );
            return page;
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
	 * Obtiene expediente asociado a un id de persistencia
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}" 
     */
	public EstadoExpediente obtenerExpedienteAnonimo(String idPersistencia )
	{
		Session session = getSession();
        try 
        {
        	if( StringUtils.isEmpty( idPersistencia ) )
        	{
        		throw new Exception ( "No se ha indicado idPersistencia" );
        	}
        	Query query = 
        		session.createQuery( "FROM EstadoExpediente AS e WHERE e.user = :idPersistencia and e.autenticado = 'N' ORDER BY e.fechaActualizacion DESC" )
        		.setParameter("idPersistencia",idPersistencia);
        	return (EstadoExpediente) query.uniqueResult();
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
	
	
}
