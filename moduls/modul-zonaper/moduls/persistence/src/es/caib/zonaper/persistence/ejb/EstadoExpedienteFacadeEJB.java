package es.caib.zonaper.persistence.ejb;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.lang.StringUtils;

import es.caib.zonaper.model.EstadoExpediente;
import es.caib.zonaper.model.Page;

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
     * @ejb.permission role-name="${role.user}"
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
     * @ejb.permission role-name="${role.user}" 
     */
	public Page busquedaPaginadaExpedientes(int pagina, int longitudPagina )
	{
		Session session = getSession();
        try 
        {
        	String seyconCiudadano = this.ctx.getCallerPrincipal().getName();
        	Query query = 
        		session.createQuery( "FROM EstadoExpediente AS e WHERE e.user = :seyconCiudadano and e.autenticado = 'S' ORDER BY e.fechaActualizacion DESC,e.id DESC" )
        		.setParameter("seyconCiudadano",seyconCiudadano);
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
     * @ejb.permission role-name="${role.user}" 
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
